package info.zznet.znms.web.module.system.controller;

import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.entity.SmAdmin;
import info.zznet.znms.base.entity.SmRole;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.annotation.DoLog;
import info.zznet.znms.web.module.common.page.Order;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.system.bean.SmAdminBean;
import info.zznet.znms.web.module.system.service.AdminManageService;
import info.zznet.znms.web.util.ExcelUtil;
import info.zznet.znms.web.util.MessageUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 * @version 1.0
 * @author yuanjingtao
 *
 */
@Controller
@RequestMapping("/adminManage")
public class AdminManageController extends BaseController{
	//管理员主页面
	private static final String MAIN = "system/adminManage_main";
	//角色实体list
	private static final String ATTR_ROLE_LIST = "roleList";
	//下拉框角色列表
	private static final String ATTR_ROLE_SOURCE = "roleSource";
	//用来查询出SmAdmin的service
	@Autowired
    private AdminManageService<SmAdmin,SmAdminBean> adminManageService;
	//用来查询出SmAdminBean的service
	@Autowired
    private AdminManageService<SmAdminBean,SmAdminBean> adminManageService2;
	
	/**
	 * 得到显示数据
	 * @param pager 分页bean
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_ADMIN_MANAGE_VIEW)
	public String doSearch(Pager<SmAdminBean> pager,HttpServletRequest request,HttpSession session) throws JsonGenerationException, JsonMappingException, IOException {
		return JSONObject.fromObject(adminManageService2.findPagedObjects(pager, Order.create("ADMIN_UUID","asc")), getJsonConfig()).toString();
	}
	
	/**
	 * 删除选中数据（复数）
	 * @param jsonData 选中的数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_ADMIN_MANAGE_DELETE)
	@DoLog("删除用户")
	public Pager<SmAdmin> deleteMulti(@RequestParam("jsonData") String jsonData,HttpServletRequest request) {
		JSONArray jsonArray = JSONArray.fromObject(jsonData);
		List<String> uuids = new ArrayList<String>();
		String pkId = "adminUuid";
		for(int i=0,j=jsonArray.size();i<j;i++){
			uuids.add((String) jsonArray.getJSONObject(i).get(pkId));
		}
		adminManageService.deleteByUuidList(uuids);
		// 显示指定数据被删除后的画面
		return adminManageService.findPagedObjects(new Pager<SmAdmin>(), Order.create("ADMIN_ID","asc"));
	}
	
	/**
	 * 删除单一数据
	 * @param jsonData 即将删除的数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteSingle", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_ADMIN_MANAGE_DELETE)
	@DoLog("删除用户")
	public String deleteSingle(@RequestParam("jsonData") String jsonData,HttpServletRequest request){
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		if(jsonObject.getString("adminId").equals("admin")){
			return "{\"result\":false,\"msg\":\"不可删除系统预配置用户\"}";
		}
		adminManageService.deleteByPrimaryKey(jsonObject.getString("adminUuid"));
		return "{\"result\":true}";
	}
	
	/** 显示模块主画面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "")
	@CheckPermission(PermissionConstants.P_ADMIN_MANAGE_VIEW)
	public ModelAndView init(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(MAIN);
		mav.addObject(ATTR_ROLE_LIST, JSONArray.fromObject(adminManageService.findRoleList()).toString());
		
		List<Map<String, Object>> roleSource = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> defaultMap = new HashMap<String, Object>();
		defaultMap.put("text", MessageUtil.getMessage("smAdmin.option.findRoleName"));
		defaultMap.put("value","");
		roleSource.add(defaultMap);
		
		List<SmRole> list = adminManageService.findRoleList();
		
		for(SmRole role : list){
			Map<String, Object> textMap = new HashMap<String, Object>();
			textMap.put("text", role.getRoleName());
			textMap.put("value",role.getRoleUuid());
			roleSource.add(textMap);
		}
		mav.addObject(ATTR_ROLE_SOURCE, JSONArray.fromObject(roleSource).toString());
		return mav;
	}	
	
	/**
	 * 查找管理员
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/search/{id}", method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    @CheckPermission(PermissionConstants.P_ADMIN_MANAGE_DETAIL)
    public String searchById(@PathVariable("id")String id){
    	return JSONObject.fromObject(adminManageService.findByPrimaryKey(id),getJsonConfig()).toString();
    }
    
	/**
	 * 新增/修改管理员
	 * @param smAdmin
	 * @param bindingResult
	 * @param request
	 * @param attr
	 * @return
	 */
    @RequestMapping(value = "/merge/do", method = RequestMethod.POST)
    @ResponseBody
    @CheckPermission(PermissionConstants.P_ADMIN_MANAGE_ADD+"||"+PermissionConstants.P_ADMIN_MANAGE_EDIT)
    public boolean doMerge(@ModelAttribute @Valid SmAdmin smAdmin,BindingResult bindingResult, HttpServletRequest request,RedirectAttributes attr){
    	if(bindingResult.hasErrors()){
    		return false;
    	}
    	if(!StringUtil.isNullString(smAdmin.getAdminUuid())){
    		//update
    		SmAdmin entity = adminManageService.findByPrimaryKey(smAdmin.getAdminUuid());
    		entity.setAdminId(smAdmin.getAdminId());
    		entity.setAdminName(smAdmin.getAdminName());
    		if(!StringUtil.isNullString(smAdmin.getAdminPwd())){
    			entity.setAdminPwd(smAdmin.getAdminPwd());
    		}
    		entity.setContactNumber(smAdmin.getContactNumber());
    		entity.setRoleUuid(smAdmin.getRoleUuid());
    		entity.setStatus(smAdmin.getStatus());
    		int count = adminManageService.updateByPrimaryKey(entity);
    		if(count==0){
    			attr.addFlashAttribute(SystemConstants.MESSAGE_TYPE_RESULT_MSG, SystemConstants.MESSAGE_TYPE_UPDATE_FAILED);
    		}else{
    			attr.addFlashAttribute(SystemConstants.MESSAGE_TYPE_RESULT_MSG, SystemConstants.MESSAGE_TYPE_UPDATE_SUCCESS);
    		}
    	} else {
    		//insert
    		int count = adminManageService.insert(smAdmin);
    		if(count==0){
    			attr.addFlashAttribute(SystemConstants.MESSAGE_TYPE_RESULT_MSG, SystemConstants.MESSAGE_TYPE_SAVE_SUCCESS);
    		}else{
    			attr.addFlashAttribute(SystemConstants.MESSAGE_TYPE_RESULT_MSG, SystemConstants.MESSAGE_TYPE_SAVE_SUCCESS);
    		}
    	}
    	return true;
    }
    
    /**
	 * 导出符合条件的数据
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/export",method=RequestMethod.GET)
	public void export(Pager<SmAdminBean> pager, HttpServletResponse response,HttpServletRequest request){
		pager.setLimit(10000);
		pager = adminManageService2.findPagedObjects(pager);
		ExcelUtil<SmAdminBean> excelUtil = new ExcelUtil<SmAdminBean>();
		excelUtil.exportExcel(MessageUtil.getMessage("smAdmin.title.excel"),pager.getRows(), response,request);
	}
	
	/**
	 * 检测用户名是否重复
	 * @param adminId
	 * @return
	 */
	@RequestMapping("/checkAccount")
    @ResponseBody
    public Boolean checkAccount(@RequestParam("adminId")String adminId){
    	return adminManageService.findByAdminId(adminId) == 0;
    }
    
	/**
	 * 分页查询的JsonConfig配置
	 * @return
	 */
	public JsonConfig getJsonConfig(){
    	JsonConfig jsonCfg = new JsonConfig();
    	jsonCfg.setIgnoreDefaultExcludes(false);
		jsonCfg.setExcludes(new String[]{});
		// 防止自包含引发的死循环
		jsonCfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		
		final String notActivated = MessageUtil.getMessage("smAdmin.option.status.notActivated");
		final String activated = MessageUtil.getMessage("smAdmin.option.status.activated");
		final String disabled = MessageUtil.getMessage("smAdmin.option.status.disabled");
		
		jsonCfg.registerJsonValueProcessor(SmAdminBean.class, "status", new JsonValueProcessor() {

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
						returndesc = "<span class=\"red\">"+notActivated+"</span>";break;
					case 1:
						returndesc = "<span class=\"green\">√"+activated+"</span>";break;
					case 2:
						returndesc = "<span class=\"grey\">"+disabled+"</span>";break;
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
