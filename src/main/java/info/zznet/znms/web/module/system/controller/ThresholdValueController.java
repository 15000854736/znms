/**
 * 
 */
package info.zznet.znms.web.module.system.controller;

import info.zznet.znms.base.bean.SearchListBean;
import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.entity.ThresholdValue;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.util.TimeUtil;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.system.service.ThresholdValueService;
import info.zznet.znms.web.module.thresholdValueAlarm.service.ThresholdValueLogService;
import info.zznet.znms.web.module.thresholdValueAlarm.service.ThresholdValueTriggerSurveyService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@Controller
@RequestMapping("thresholdValue")
public class ThresholdValueController extends BaseController{

	@Autowired
	private ThresholdValueService thresholdValueService;
	
	@Autowired
	private ThresholdValueLogService thresholdValueLogService;
	
	@Autowired
	private ThresholdValueTriggerSurveyService thresholdValueTriggerSurveyService;
	
	private static final String VIEW_MAIN = "system/thresholdValue/thresholdValue_main";
	
	private static final String VIEW_DETAIL = "system/thresholdValue/thresholdValue_detail";

	/**
	 * 跳转到阈值管理主页面
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView init(){
		return new ModelAndView(VIEW_MAIN);
	}
	
	/**
	 * 查找数据
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_THRESHOLD_VALUE_VIEW)
	public String doSearch(Pager<ThresholdValue> pager) throws Exception {
		pager = thresholdValueService.findPageList(pager);
		return JSONObject.fromObject(pager,getJsonConfig()).toString();
	}
	
	/**
	 * 查找最近一小时阀值日志
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getThresholdValueData", method = RequestMethod.POST)
	@ResponseBody
	public String getThresholdValueData() throws Exception {

		List<ThresholdValue>  thresholdValueList= null;
		try {
			thresholdValueList = thresholdValueService.findByTime(TimeUtil.getLastHour(-60),TimeUtil.getLastHour(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(thresholdValueList.size()>0){
			return JSONObject.fromObject(thresholdValueList).toString();
		}else{
			return  "{\"length\":0}";
		}
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
		//转换流向
		jsonCfg.registerJsonValueProcessor(ThresholdValue.class, "flowDirection", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					Integer status = (Integer)o;
					int value = status.intValue();
					String returndesc;
					switch(value){
					case 0:
						returndesc = "";break;
					case 1:
						returndesc = "下行";break;
					case 2:
						returndesc = "上行";break;
					default:
						returndesc = "未知";
					}
					return returndesc;
				} else {
					return "";
				}
			}
		});
		
		//转换状态
		jsonCfg.registerJsonValueProcessor(ThresholdValue.class, "status", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					Integer status = (Integer)o;
					int value = status.intValue();
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
		
		return jsonCfg;
    }
	
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	@CheckPermission(PermissionConstants.P_THRESHOLD_VALUE_ADD)
	public ModelAndView initAddPage(){
		return new ModelAndView(VIEW_DETAIL).addObject("thresholdValue", new ThresholdValue()).addObject("page", "add");
	}
	
	/**
	 * 添加、修改阈值
	 * @param thresholdValue
	 * @return
	 */
	@RequestMapping(value = "/mergeDo", method = RequestMethod.POST)
	@ResponseBody
	public String mergeDo(ThresholdValue thresholdValue){
		//校验阈值名是否重复
		ThresholdValue value = thresholdValueService.checkThresholdValueName(thresholdValue.getThresholdValueUuid(), 
				thresholdValue.getThresholdValueName());
		if(null!=value){
			return "{\"result\":false,\"msg\":\"阈值名称重复\"}";
		}
		if(StringUtil.isNullString(thresholdValue.getThresholdValueUuid())){
			//添加阈值
			return thresholdValueService.add(thresholdValue);
		}else{
			//修改阈值
			thresholdValueService.update(thresholdValue);
			return "{\"result\":true}";
		}
	}
	
	/**
	 * 跳转到详情页面、编辑页面
	 * @param action
	 * @param thresholdValueUuid
	 * @return
	 */
	@RequestMapping(value = "/{action}/{thresholdValueUuid}", method = RequestMethod.GET)
	public ModelAndView intiDetailPage(@PathVariable("action")String action, 
			@PathVariable("thresholdValueUuid")String thresholdValueUuid){
		ModelAndView mav = new ModelAndView(VIEW_DETAIL);
		ThresholdValue thresholdValue = thresholdValueService.selectByPrimaryKey(thresholdValueUuid);
		mav.addObject("thresholdValue", thresholdValue);
		if("edit".equals(action)){
			mav.addObject("page", "edit");
		}else{
			mav.addObject("page", "detail");
		}
		return mav;
	}
	
	/**
	 * 删除单个阈值
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/deleteSingle", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_THRESHOLD_VALUE_DELETE)
	public String deleteSingle(@RequestParam("jsonData") String jsonData){
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
   		String thresholdValueUuid = jsonObject.getString("thresholdValueUuid");
   		thresholdValueService.deleteByPrimaryKey(thresholdValueUuid);
   		thresholdValueLogService.deleteByThresHoldValueUUId(thresholdValueUuid);
   		thresholdValueTriggerSurveyService.deleteByThresHoldValueUUId(thresholdValueUuid);
		return "{\"result\":true}";
	}
	
	/**
	 * 删除多个阈值
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_THRESHOLD_VALUE_DELETE)
	public String deleteMulti(@RequestParam("jsonData") String jsonData){
		JSONArray jsonArray = JSONArray.fromObject(jsonData);
   		List<String> uuids = new ArrayList<String>();
   		String pkId = "thresholdValueUuid";
   		for(int i=0,j=jsonArray.size();i<j;i++){
   			uuids.add((String) jsonArray.getJSONObject(i).get(pkId));
   		}
   		thresholdValueService.deleteThresholdValueList(uuids);
   		return JSONObject.fromObject(thresholdValueService.findPageList(new Pager())).toString();
	}
	
	/**
   	 * 启用阈值
   	 * @param ids
   	 * @return
   	 */
   	@RequestMapping(value = "/enable", method = RequestMethod.POST)
   	@CheckPermission(PermissionConstants.P_THRESHOLD_VALUE_ENABLE)
   	@ResponseBody
   	public String enableThresholdValue(String ids){
   		List<String> uuidList = Arrays.asList(ids.split(","));
   		thresholdValueService.updateThresholdValueEnable(uuidList);
   		return  "{\"result\":true}";
   	}
   	
   	/**
   	 * 禁用阈值
   	 * @param ids
   	 * @return
   	 */
   	@RequestMapping(value = "/disable", method = RequestMethod.POST)
   	@CheckPermission(PermissionConstants.P_THRESHOLD_VALUE_DISABLE)
   	@ResponseBody
   	public String disableThresholdValue(String ids){
   		List<String> uuidList = Arrays.asList(ids.split(","));
   		thresholdValueService.updateThresholdValueDisable(uuidList);
   		return  "{\"result\":true}";
   	}
   	
   	/**
   	 * 查找阈值json数据
   	 * @return
   	 */
   	@RequestMapping(value = "/findThresholdValueJson", method = RequestMethod.POST)
   	@ResponseBody
   	public String findThresholdValueJson(){
   		List<ThresholdValue> valueList = thresholdValueService.findAll();
   		List<SearchListBean> beanList = new ArrayList<SearchListBean>();
   		beanList.add(new SearchListBean("-1","请选择阈值"));
   		for (ThresholdValue value : valueList) {
   			beanList.add(new SearchListBean(value.getThresholdValueUuid(),value.getThresholdValueName()));
		}
   		return JSONArray.fromObject(beanList).toString();
   	}
}
