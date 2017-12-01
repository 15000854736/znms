/**
 * 
 */
package info.zznet.znms.web.module.systemLog;

import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.entity.SystemLog;
import info.zznet.znms.base.entity.SystemLogStatistics;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.util.TimeUtil;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.common.constants.SystemLogFacility;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.systemLog.service.SystemLogService;
import info.zznet.znms.web.util.ExcelUtil;
import info.zznet.znms.web.util.MessageUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

/**
 * @author dell001
 *
 */
@Controller
@RequestMapping("/systemLog")
public class SystemLogController extends BaseController{
	
	@Autowired
	private SystemLogService systemLogService;
	
	private static final String VIEW_MAIN = "systemLog/systemLog_main";
	
	private static final String ATTR_DEFAULT_TIME_FROM = "defaultLogTimeFrom";
	
	private static final String ATTR_DEFAULT_TIME_TO = "defaultLogTimeTo";

	/**
	 * 初始化系统日志页面
	 * @return
	 */
	@RequestMapping("")
	@CheckPermission(PermissionConstants.P_SYSTEM_LOG_VIEW)
	public ModelAndView init(){
		return new ModelAndView(VIEW_MAIN).addObject(ATTR_DEFAULT_TIME_FROM, StringUtil.getTodayStartDate())
				.addObject(ATTR_DEFAULT_TIME_TO, StringUtil.getTodayEndDate());
	}
	
	/**
	 * 查找主页面显示数据
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_SYSTEM_LOG_VIEW)
	public String doSearch(Pager pager){
		pager = systemLogService.findPageList(pager);
		return JSONObject.fromObject(pager, getJsonConfig()).toString();
	}
	
	/**
	 * 获取最近一小时的系统日志
	 * @return
	 */
	@RequestMapping(value = "/getSystemLog", method = RequestMethod.POST)
	@ResponseBody
	public String getSystemLog(){
		List<SystemLogStatistics>  systemLogStatisticsList= systemLogService.findLogStatisticsList(TimeUtil.getLastHour(-60),TimeUtil.getLastHour(0));
		if(systemLogStatisticsList.size()>0){
			return JSONArray.fromObject(systemLogStatisticsList).toString();
		}else{
			return  "{\"length\":0}";
		}
	}
	
	
	/**
	 * @return
	 */
	private JsonConfig getJsonConfig() {
		JsonConfig jsonCfg = new JsonConfig();
    	jsonCfg.setIgnoreDefaultExcludes(false);
		jsonCfg.setExcludes(new String[]{});
		// 防止自包含引发的死循环
		jsonCfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		//转换日志级别
		jsonCfg.registerJsonValueProcessor("priorityId", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					Integer priorityId = (Integer)o;
					int value = priorityId.intValue();
					String returnDesc = "";
					switch(value){
						case 0:
							returnDesc = "<span style='color:red'>紧急</spn>";break;
						case 1:
							returnDesc = "<span style='color:#F06'>警报</spn>";break;
						case 2:
							returnDesc = "<span style='color:#F60'>严重</spn>";break;
						case 3:
							returnDesc = "<span style='color:#939'>错误</spn>";break;
						case 4:
							returnDesc = "<span style='color:#F90'>警告</spn>";break;
						case 5:
							returnDesc = "<span style='color:#0C0'>通知</spn>";break;
						case 6:
							returnDesc = "<span style='color:#369'>信息</spn>";break;
						case 7:
							returnDesc = "<span style='color:#390'>调试</spn>";break;
						case 8:
							returnDesc = "<span style='color:#390'>其他</spn>";break;
						default:
							returnDesc = "未知";
					}
					return returnDesc;
				} else {
					return "";
				}
			}
		});
		
		//转换日志类型
		jsonCfg.registerJsonValueProcessor("facilityId", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					Integer priorityId = (Integer)o;
					int value = priorityId.intValue();
					return SystemLogFacility.findValueByKey(value);
				} else {
					return "";
				}
			}
		});
		
		
		//转换日志生成时间
		jsonCfg.registerJsonValueProcessor("logTime", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if(o != null){
 					Date logTime = (Date) o;
 					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
 					String time = sdf.format(logTime);
 					return time;
 				}
 				return "";
			}
		});
		
		return jsonCfg;
	}
	
	/**
	 * 删除单个系统日志
	 * @return
	 */
	@RequestMapping(value = "/deleteSingle", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_SYSTEM_LOG_DELETE)
	public String deleteSingleSystemLog(@RequestParam("jsonData")String jsonData){
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		Long seq = Long.valueOf(jsonObject.getString("seq"));
		systemLogService.deleteByPrimaryKey(seq);
		return "{\"result\":true}";
	}
	
	/**
	 * 删除多个系统日志
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_SYSTEM_LOG_DELETE)
	public String deleteMultiSystemLog(@RequestParam("jsonData")String jsonData){
		JSONArray jsonArray = JSONArray.fromObject(jsonData);
		List<Long> uuids = new ArrayList<Long>();
   		String pkId = "seq";
   		for(int i=0,j=jsonArray.size();i<j;i++){
   			String seq = (String) jsonArray.getJSONObject(i).getString("seq");
   			uuids.add(Long.valueOf(seq));
   		}
   		systemLogService.deleteUuidList(uuids);
		return JSONObject.fromObject(systemLogService.findPageList(new Pager())).toString();
	}
	
	/**
     * 导出符合条件的日志
     * @param response
     * @return
     */
	@RequestMapping(value = "/export",method=RequestMethod.POST)
    @ResponseBody
    @CheckPermission(PermissionConstants.P_SYSTEM_LOG_EXPORT)
    public String export(@RequestBody Pager pager, HttpServletResponse response,HttpServletRequest request){
    	
    	JSONObject result = new JSONObject();
		result.put("status", "-1");
		result.put("msg","无导出数据");
		try {
			String searchItems = pager.getSearch();
			pager.setSearch(HtmlUtils.htmlUnescape(searchItems));
			pager.setLimit(SystemConstants.MAX_EXPORT_NUM);
			pager = systemLogService.findPageList(pager);
			if (pager.getTotal() > 0) {
				if (pager.getTotal() > SystemConstants.MAX_EXPORT_NUM) {
					result.put("msg", MessageUtil.getMessage("view.error.export.excel.too.much.data",SystemConstants.MAX_EXPORT_NUM));
				} else {
					pager = systemLogService.findPageList(pager);
					result.put("status", "0");
					// 把数据放入session中
					request.getSession().setAttribute(SystemConstants.EXPORT_DATA_SESSION_KEY,pager.getRows());
				}
			}
		} catch (Exception e) {
			result.put("status", "-1");
			result.put("msg",MessageUtil.getMessage("view.error.export.excel.excepiton"));
		}
		return result.toString();
    }
	
	/**
     * 导出
     *
     * @param response
     * @return
     */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void export_Auth(HttpServletResponse response, HttpServletRequest request) {
    	List<SystemLog> list = (List) request.getSession().getAttribute(
				SystemConstants.EXPORT_DATA_SESSION_KEY);
		request.getSession().removeAttribute(
				SystemConstants.EXPORT_DATA_SESSION_KEY);
		ExcelUtil<SystemLog> e = new ExcelUtil<SystemLog>();
		e.exportExcel("系统日志导出一览", list, response,request);
    }
	
	/**
     * 处理时间等格式
     * @param request
     * @param binder
     * @throws Exception
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request,
                ServletRequestDataBinder binder) throws Exception {
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            CustomDateEditor dateEditor = new CustomDateEditor(format, true);
            binder.registerCustomEditor(Date.class, dateEditor);
        }
}
