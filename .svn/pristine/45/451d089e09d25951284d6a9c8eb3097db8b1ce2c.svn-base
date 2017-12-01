/**
 * 
 */
package info.zznet.znms.web.module.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.entity.SystemLogDeleteRule;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.system.service.SystemLogDeleteRuleService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author dell001
 *
 */
@RequestMapping("/systemLogDeleteRule")
@Controller
public class SystemLogDeleteRuleController extends BaseController{

	@Autowired
	private SystemLogDeleteRuleService ruleService;
	
	private static final String VIEW_MAIN = "system/systemLogDeleteRule_main";
	
	
	/**
	 * 跳转到系统日志删除规则主页面
	 * @return
	 */
	@RequestMapping("")
	@CheckPermission(PermissionConstants.P_SYSTEM_LOG_DELETE_RULE_VIEW)
	public ModelAndView init(){
		return new ModelAndView(VIEW_MAIN).addObject("systemLogDeleteRule", new SystemLogDeleteRule());
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_SYSTEM_LOG_DELETE_RULE_VIEW)
	public String doSearch(Pager<SystemLogDeleteRule> pager){
		pager = ruleService.findPageList(pager);
		return JSONObject.fromObject(pager,getJsonConfig()).toString();
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
		//转换启用状态
		jsonCfg.registerJsonValueProcessor(SystemLogDeleteRule.class, "ruleStatus", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					Integer ruleStatus = (Integer)o;
					int value = ruleStatus.intValue();
					String returndesc;
					switch(value){
					case 0:
						returndesc = "禁用";break;
					case 1:
						returndesc = "启用";break;
					default:
						returndesc = "未知";
					}
					return returndesc;
				} else {
					return "";
				}
			}
		});
		
		//转换匹配类型
		jsonCfg.registerJsonValueProcessor(SystemLogDeleteRule.class, "matchType", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					Integer matchType = (Integer)o;
					int value = matchType.intValue();
					String returndesc;
					switch(value){
					case 1:
						returndesc = "以什么开始";break;
					case 2:
						returndesc = "包含";break;
					case 3:
						returndesc = "以什么结束";break;
					case 4:
						returndesc = "主机名是";break;
					case 5:
						returndesc = "功能是";break;
					case 6:
						returndesc = "SQL表达式";break;
					default:
						returndesc = "未知";
					}
					return returndesc;
				} else {
					return "";
				}
			}
		});
		
		return jsonCfg;
    }
	
	/**
	 * 添加、修改系统日志删除规则
	 * @param rule
	 * @return
	 */
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	@CheckPermission(PermissionConstants.P_SYSTEM_LOG_DELETE_RULE_ADD + "||" +
			PermissionConstants.P_SYSTEM_LOG_DELETE_RULE_EDIT)
	public ModelAndView merge(@ModelAttribute SystemLogDeleteRule rule){
		if(StringUtil.isNullString(rule.getSystemLogDeleteRuleUuid())){
			//添加规则
			ruleService.addRule(rule);
		}else{
			//修改规则
			ruleService.updateRule(rule);
		}
		return new ModelAndView("redirect:/systemLogDeleteRule");
	}
	
	/**
	 * 查找规则
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/search/{uuid}", method = RequestMethod.POST)
	@ResponseBody
	public String findRuleByUuid(@PathVariable("uuid")String uuid){
		return JSONObject.fromObject(ruleService.findRuleByUuid(uuid)).toString();
	}
	
	/**
	 * 删除单条数据
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/deleteSingle", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSingleRule(@RequestParam("jsonData") String jsonData){
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
   		String uuid = jsonObject.getString("systemLogDeleteRuleUuid");
   		ruleService.deleteByPrimaryKey(uuid);
   		return "{\"result\":true}";
	}
	
	/**
	 * 删除多条数据
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/deleteRuleList", method = RequestMethod.POST)
	@ResponseBody
	public String deleteRule(@RequestParam("jsonData") String jsonData){
		JSONArray jsonArray = JSONArray.fromObject(jsonData);
   		List<String> uuids = new ArrayList<String>();
   		String pkId = "systemLogDeleteRuleUuid";
   		for(int i=0,j=jsonArray.size();i<j;i++){
   			uuids.add((String) jsonArray.getJSONObject(i).get(pkId));
   		}
   		ruleService.deleteUuidList(uuids);
   		return JSONObject.fromObject(ruleService.findPageList(new Pager())).toString();
	}
	
	/**
   	 * 启用删除规则
   	 * @param ids
   	 * @return
   	 */
   	@RequestMapping(value = "/enable", method = RequestMethod.POST)
   	@CheckPermission(PermissionConstants.P_SYSTEM_LOG_DELETE_RULE_ENABLE)
   	@ResponseBody
   	public String enableHost(String ids){
   		List<String> uuidList = Arrays.asList(ids.split(","));
   		ruleService.updateRuleEnable(uuidList);
   		return  "{\"result\":true}";
   	}
   	
   	/**
   	 * 禁用删除规则
   	 * @param ids
   	 * @return
   	 */
   	@RequestMapping(value = "/disable", method = RequestMethod.POST)
   	@CheckPermission(PermissionConstants.P_SYSTEM_LOG_DELETE_RULE_DISABLE)
   	@ResponseBody
   	public String disableHost(String ids){
   		List<String> uuidList = Arrays.asList(ids.split(","));
   		ruleService.updateRuleDisable(uuidList);
   		return  "{\"result\":true}";
   	}
   	
   	/**
   	 * 校验系统日志删除规则名称是否重复
   	 * @param systemLogDeleteRuleUuid
   	 * @param ruleName
   	 * @return
   	 */
   	@RequestMapping(value = "/checkRuleRepeat", method = RequestMethod.POST)
   	@ResponseBody
   	public boolean checkRuleRepeat(@RequestParam("systemLogDeleteRuleUuid")String systemLogDeleteRuleUuid,
   			@RequestParam("ruleName")String ruleName){
   		return ruleService.checkRuleRepeat(systemLogDeleteRuleUuid, ruleName)==null ?false:true;
   	}

}
