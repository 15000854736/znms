/**
 * 
 */
package info.zznet.znms.web.module.thresholdValueAlarm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.entity.ThresholdValue;
import info.zznet.znms.base.entity.ThresholdValueTriggerLog;
import info.zznet.znms.base.entity.ThresholdValueTriggerSurvey;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.system.service.HostService;
import info.zznet.znms.web.module.system.service.ThresholdValueService;
import info.zznet.znms.web.module.thresholdValueAlarm.service.ThresholdValueLogService;
import info.zznet.znms.web.module.thresholdValueAlarm.service.ThresholdValueTriggerSurveyService;

/**
 * @author dell001
 *
 */
@Controller
@RequestMapping("/thresholdValueAlarm")
public class ThresholdValueAlarmController extends BaseController{

	@Autowired
	private ThresholdValueLogService logService;
	
	@Autowired
	private HostService hostService;
	
	@Autowired
	private ThresholdValueService thresholdValueService;
	
	@Autowired
	private ThresholdValueTriggerSurveyService surveyService;
	
	private static final String VIEW_TRIGGER_LOG = "thresholdValueAlarm/thresholdValueAlarm_triggerLog";
	
	private static final String VIEW_TRIGGER_SURVEY = "thresholdValueAlarm/thresholdValueAlarm_triggerSurvey";
	
	/**
	 * 跳转到阀值触发日志主页面
	 * @return
	 */
	@RequestMapping("")
	@CheckPermission(PermissionConstants.P_THRESHOLD_VALUE_TRIGGER_LOG_VIEW)
	public ModelAndView toTriggerLogPage(){
		return new ModelAndView(VIEW_TRIGGER_LOG);
	}
	
	/**
	 * 查询阀值触发日志列表数据
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/triggerLog/search", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_THRESHOLD_VALUE_TRIGGER_LOG_VIEW)
	public String doSearchTriggerLog(Pager<ThresholdValueTriggerLog> pager){
		pager = logService.findPageList(pager);
		return JSONObject.fromObject(pager, getTriggerLogJsonConfig()).toString();
	}
	
	private JsonConfig getTriggerLogJsonConfig(){
		JsonConfig jsonCfg = new JsonConfig();
    	jsonCfg.setIgnoreDefaultExcludes(false);
		jsonCfg.setExcludes(new String[]{});
		// 防止自包含引发的死循环
		jsonCfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		//转换主机名
		jsonCfg.registerJsonValueProcessor(ThresholdValueTriggerLog.class, "hostUuid", new JsonValueProcessor() {

			 @Override
	            public Object processArrayValue(Object o, JsonConfig jsonConfig) {
	                return null;
	            }

	            @Override
	            public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
	            	 if (null != o) {
	                     String hostUuid = (String)o;
	                     Host host = hostService.findHostById(hostUuid);
	                     return host==null ?"未知":host.getHostName();
	            	 }
	            	 return "";
	            }
	            	 
		});
		//转换阀值名
		jsonCfg.registerJsonValueProcessor(ThresholdValueTriggerLog.class, "thresholdValueUuid", new JsonValueProcessor() {

			 @Override
	            public Object processArrayValue(Object o, JsonConfig jsonConfig) {
	                return null;
	            }

	            @Override
	            public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
	            	 if (null != o) {
	                     String thresholdValueUuid = (String)o;
	                     ThresholdValue thresholdValue = thresholdValueService.selectByPrimaryKey(thresholdValueUuid);
	                     return thresholdValue==null ?"未知":thresholdValue.getThresholdValueName();
	            	 }
	            	 return "";
	            }
	            	 
		});
		//转换时间
		jsonCfg.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				return null;
			}

			@Override
			public Object processObjectValue(String arg0, Object arg1,
					JsonConfig arg2) {
				if(arg1 != null){
					
					Date date01 = (Date) arg1;
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					String time = sdf.format(date01);
					return time;
				}
				return "";
			}
			
		});
		
		return jsonCfg;
	}
	
	/**
	 * 删除单个阀值触发日志
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/triggerLog/deleteSingle", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_THRESHOLD_VALUE_TRIGGER_LOG_DELETE)
	public String deleteSingleTriggerLog(@RequestParam("jsonData")String jsonData){
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
   		String logUuid = jsonObject.getString("logUuid");
   		logService.deleteByPrimaryKey(logUuid);
   		return "{\"result\":true}";
	}
	
	/**
	 * 删除多个阀值触发日志
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/triggerLog/delete", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_THRESHOLD_VALUE_TRIGGER_LOG_DELETE)
	public String deleteTriggerLogList(@RequestParam("jsonData") String jsonData){
		JSONArray jsonArray = JSONArray.fromObject(jsonData);
   		List<String> uuids = new ArrayList<String>();
   		String pkId = "logUuid";
   		for(int i=0,j=jsonArray.size();i<j;i++){
   			uuids.add((String) jsonArray.getJSONObject(i).get(pkId));
   		}
   		logService.deleteByUuidList(uuids);
   		return JSONObject.fromObject(logService.findPageList(new Pager())).toString();
	}
	
	/**
	 * 跳转到阀值触发概况主页面
	 * @return
	 */
	@RequestMapping("/triggerSurvey")
	public ModelAndView toTriggerSurveyPage(){
		return new ModelAndView(VIEW_TRIGGER_SURVEY);
	}
	
	/**
	 * 查询阀值触发概况列表数据
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/triggerSurvey/search", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_THRESHOLD_VALUE_TRIGGER_SURVEY_VIEW)
	public String doSearchTriggerSurvey(Pager<ThresholdValueTriggerSurvey> pager){
		pager = surveyService.findPageList(pager);
		return JSONObject.fromObject(pager, getTriggerSurveyJsonConfig()).toString();
	}
	
	private JsonConfig getTriggerSurveyJsonConfig(){
		JsonConfig jsonCfg = new JsonConfig();
    	jsonCfg.setIgnoreDefaultExcludes(false);
		jsonCfg.setExcludes(new String[]{});
		// 防止自包含引发的死循环
		jsonCfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		//转换阀值名
		jsonCfg.registerJsonValueProcessor(ThresholdValueTriggerSurvey.class, "thresholdValueUuid", new JsonValueProcessor() {

			 @Override
	            public Object processArrayValue(Object o, JsonConfig jsonConfig) {
	                return null;
	            }

	            @Override
	            public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
	            	 if (null != o) {
	                     String thresholdValueUuid = (String)o;
	                     ThresholdValue thresholdValue = thresholdValueService.selectByPrimaryKey(thresholdValueUuid);
	                     return thresholdValue==null ?"未知":thresholdValue.getThresholdValueName();
	            	 }
	            	 return "";
	            }
	            	 
		});
		return jsonCfg;
	}
	
	/**
	 * 删除单个阀值触发概况
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/triggerSurvey/deleteSingle", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_THRESHOLD_VALUE_TRIGGER_SURVEY_DELETE)
	public String deleteSingleTriggerSurvey(@RequestParam("jsonData")String jsonData){
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
   		String surveyUuid = jsonObject.getString("surveyUuid");
   		surveyService.deleteByPrimaryKey(surveyUuid);
   		return "{\"result\":true}";
	}
	
	/**
	 * 删除多个阀值触发概况
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/triggerSurvey/delete", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_THRESHOLD_VALUE_TRIGGER_SURVEY_DELETE)
	public String deleteTriggerSurveyList(@RequestParam("jsonData") String jsonData){
		JSONArray jsonArray = JSONArray.fromObject(jsonData);
   		List<String> uuids = new ArrayList<String>();
   		String pkId = "surveyUuid";
   		for(int i=0,j=jsonArray.size();i<j;i++){
   			uuids.add((String) jsonArray.getJSONObject(i).get(pkId));
   		}
   		surveyService.deleteByUuidList(uuids);
   		return JSONObject.fromObject(logService.findPageList(new Pager())).toString();
	}
}
