package info.zznet.znms.web.module.system.controller;

import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.dao.SmRoleMapper;
import info.zznet.znms.base.entity.SmAdmin;
import info.zznet.znms.base.entity.SmRole;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.annotation.DoLog;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.security.bean.SessionBean;
import info.zznet.znms.web.module.system.service.RolePermissionService;
import info.zznet.znms.web.util.MessageUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rolePermission")
public class RolePermissionController extends BaseController{
	private static final String VIEW_MAIN = "system/rolePermission_main";
	
	public static final String ATTR_ROLE_NAME = "roleName";
	public static final String ATTR_DESCRIPTION = "description";
	public static final String ATTR_PERMISSIONS = "permissions";
	
	@Autowired
	private SmRoleMapper roleMapper;
	
	@Autowired
	private RolePermissionService<SmRole,SmRole> rolePermissionService;
	
	@RequestMapping("")
	@CheckPermission(PermissionConstants.P_ROLE_PERMISSION_VIEW)
	public ModelAndView init(){
		return new ModelAndView(VIEW_MAIN);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/search", method=RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_ROLE_PERMISSION_VIEW)
	public String doSearch(Pager<SmRole> pager, HttpServletRequest request, HttpSession session) throws JsonGenerationException, JsonMappingException, IOException {
		pager = rolePermissionService.findPageList(pager); 
		return JSONObject.fromObject(pager,getJsonConfig()).toString();
	}
    
	/**
	 * 删除单一数据
	 * @param jsonData 即将删除的数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteSingle", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_ROLE_PERMISSION_DELETE)
	@DoLog("删除角色")
	public String deleteSingle(@RequestParam("jsonData") String jsonData,HttpServletRequest request){
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		String roleUuid = jsonObject.getString("roleUuid");
		if(rolePermissionService.findAdminCntByRole(roleUuid)>0){
			return "{\"result\":false,\"msg\":\"存在使用该角色的用户，无法删除\"}";
		} else if(roleUuid.equals("1")){
			return "{\"result\":false,\"msg\":\"无法删除系统预配置角色\"}";
		}
		
		rolePermissionService.deleteByUuid(roleUuid);
		return "{\"result\":true}";
	}
	
	/**
	 * 删除选中数据（复数）
	 * @param jsonData 选中的数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_ROLE_PERMISSION_DELETE)
	@DoLog("删除角色")
	public String deleteMulti(@RequestParam("jsonData") String jsonData,HttpServletRequest request) {
		
		JSONArray jsonArray = JSONArray.fromObject(jsonData);
		List<String> uuids = new ArrayList<String>();
		String pkId = "roleUuid";
		Boolean canDelete = true;
		StringBuffer cantDeleteRole = new StringBuffer(50);
		for(int i=0,j=jsonArray.size();i<j;i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			uuids.add((String) jsonObject.get(pkId));
			if(rolePermissionService.findAdminCntByRole((String) jsonObject.get(pkId))>0){
				canDelete = false;
				cantDeleteRole.append("[").append(jsonObject.get("roleName")).append("]  ");
			}
		}
		if(!canDelete){
			return "{\"result\":false,\"msg\":\"存在关联的用户，无法删除："+ cantDeleteRole+"\"}";
		}
		rolePermissionService.deleteByUuidList(uuids);
		// 显示指定数据被删除后的画面
//		return rolePermissionService.findPagedData(new Pager<SiSchoolInfo>());
		return "{\"result\":true}";
	}
    
    @RequestMapping(value="/search/{id}", method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    @CheckPermission(PermissionConstants.P_ROLE_PERMISSION_DETAIL)
    public String searchById(@PathVariable("id")String id){
    	return JSONObject.fromObject(rolePermissionService.findById(id)).toString();
    }
	
   @RequestMapping(value = "/merge/do", method = RequestMethod.POST)
	@CheckPermission(PermissionConstants.P_ROLE_PERMISSION_ADD + "||"
			+ PermissionConstants.P_ROLE_PERMISSION_EDIT)
   @ResponseBody
    public boolean doMerge(@ModelAttribute @Valid SmRole role,BindingResult bindingResult, @RequestParam(value="permissions", required=false)String permissions,HttpServletRequest request, HttpSession session){
    	SmAdmin operator = ((SessionBean)session.getAttribute(SystemConstants.SESSION_BEAN_KEY)).getSmAdmin();
    	if(bindingResult.hasErrors()){
    		return true;
    	}
    	if(!StringUtil.isNullString(role.getRoleUuid())){
    		// 更新
    		SmRole smRole = roleMapper.findByPrimaryKey(role.getRoleUuid());
    		smRole.setUpdateTime(new Date());
    		smRole.setUpdateAdmin(operator.getAdminName());
    		smRole.setStatus(role.getStatus());
    		rolePermissionService.updateRole(smRole, permissions);
			ZNMSLogger.info(operator.getAdminId()+"对角色["
					+ smRole.getRoleName() + "]执行[角色信息更改]操作");
    	} else {
    		// 新增
    		role.setRoleUuid(UUIDGenerator.getGUID());
    		role.setUpdateTime(new Date());
    		role.setUpdateAdmin(operator.getAdminName());
    		role.setCreateTime(new Date());
    		role.setCreateAdmin(operator.getAdminName());
    		rolePermissionService.addRole(role, permissions);
    		ZNMSLogger.info(operator.getAdminId()+
					"执行[新增角色]操作添加角色[" + role.getRoleName() + "]");
    	}
    	return true;
    }
   
   /**
    * 校验角色名是否重复
    * @param roleName
    * @return
    */
   @RequestMapping("/checkRole")
   @ResponseBody
   public Boolean checkRole(@RequestParam("roleUuid")String roleUuid, @RequestParam("roleName")String roleName){
	   	Map<String, Object> role = null;
	   	if(!StringUtil.isNullString(roleUuid)){
	   		role = rolePermissionService.findById(roleUuid);
	   	}
	   	if(role != null && !StringUtil.isNullString((String) role.get("roleName"))){
	   		return ((String) role.get("roleName")).equals(roleName) || rolePermissionService.findCntByRoleName(roleName) == 0;
	   	} else {
	   		return rolePermissionService.findCntByRoleName(roleName) == 0;
	   	}
   }
   
   
   public JsonConfig getJsonConfig(){
   	JsonConfig jsonCfg = new JsonConfig();
   	jsonCfg.setIgnoreDefaultExcludes(false);
		jsonCfg.setExcludes(new String[]{});
		// 防止自包含引发的死循环
		jsonCfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		
		final String activated = MessageUtil.getMessage("rolePermission.option.status.activated");
		final String disabled = MessageUtil.getMessage("rolePermission.option.status.disabled");
		
		jsonCfg.registerJsonValueProcessor(SmRole.class, "status", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					Integer status = (Integer)o;
					Integer value = status.intValue();
					String returndesc;
					switch(value){
					case 0:
						returndesc = "<span class=\"red\">"+disabled+"</span>";break;
					case 1:
						returndesc = "<span class=\"green\">√"+activated+"</span>";break;
					default:
						returndesc = "";
					}
					return returndesc;
				} else {
					return "";
				}
			}
		});
		return jsonCfg;
   }
}
