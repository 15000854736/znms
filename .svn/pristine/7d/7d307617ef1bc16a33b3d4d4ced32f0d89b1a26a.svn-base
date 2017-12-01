/**
 * 
 */
package info.zznet.znms.web.module.systemLog;

import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.entity.SystemLogStatistics;
import info.zznet.znms.base.util.DateUtil;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.common.constants.SystemLogFacility;
import info.zznet.znms.web.module.common.constants.SystemLogPriority;
import info.zznet.znms.web.module.systemLog.bean.SystemLogPicBean;
import info.zznet.znms.web.module.systemLog.service.SystemLogStatisticsService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/systemLogStatistics")
public class SystemLogStatisticsController extends BaseController{

	@Autowired
	private SystemLogStatisticsService logStatisticsService;
	
	private static final String VIEW_MIAN = "systemLog/systemLogStatistics_main";
	
	/**
	 * 跳转到日志统计页面
	 * @return
	 */
	@RequestMapping("")
	@CheckPermission(PermissionConstants.P_SYSTEM_LOG_STATISTICS_VIEW)
	public ModelAndView init(){
		ModelAndView mav = new ModelAndView(VIEW_MIAN);
		//开始时间
		mav.addObject("timeStart", StringUtil.getTodayStartDate());
		//结束时间
		mav.addObject("timeEnd", StringUtil.getTodayEndDate());
		return mav;
		
	}
	
	/**
	 * 查找饼图系统日志数据
	 * @return
	 */
	@RequestMapping(value = "/findPieData", method = RequestMethod.POST)
	@ResponseBody
	public String findPieDataByPriority(@RequestParam("id")String id,
			@RequestParam("hostId")String hostId, @RequestParam("insertTimeFrom")String insertTimeFrom,
			@RequestParam("insertTimeTo")String insertTimeTo, @RequestParam("type")Integer type){
		List<SystemLogStatistics> logList = logStatisticsService.findPieData(id, hostId, insertTimeFrom, insertTimeTo, type);
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		List<SystemLogPicBean> beanList = new ArrayList<SystemLogPicBean>();
		if(null != logList && logList.size()>0){
			if(type.intValue()==1){
				//按级别查找
				for (SystemLogStatistics systemLogStatistics : logList) {
					String name = SystemLogPriority.findValueByKey(systemLogStatistics.getPriorityId());
					generatePieMap(map, name, systemLogStatistics);
				}
			}else{
				for (SystemLogStatistics systemLogStatistics : logList) {
					String name = SystemLogFacility.findValueByKey(systemLogStatistics.getFacilityId());
					generatePieMap(map, name, systemLogStatistics);
				}
			}
			for(String key:map.keySet()){
				beanList.add(new SystemLogPicBean(map.get(key), key));
			}
			// 按记录数降序排序
			Collections.sort(beanList, new Comparator<SystemLogPicBean>() {
	            public int compare(SystemLogPicBean o1, SystemLogPicBean o2) {
	                Integer records1 = (Integer) o1.getValue();
	                Integer redords2 = (Integer) o2.getValue();
	                return redords2.compareTo(records1);
	            }
	        });
		}
		return JSONArray.fromObject(beanList).toString();
	}
	
	/**
	 * 组装饼图日志级别或类型map数据
	 * @param map
	 * @param name
	 * @param systemLogStatistics
	 */
	private void generatePieMap(Map<String, Integer> map, String name, SystemLogStatistics systemLogStatistics){
		if(map.containsKey(name)){
			map.put(name, map.get(name)+systemLogStatistics.getRecords());
		}else{
			map.put(name, systemLogStatistics.getRecords());
		}
	}
	
	
	/**
	 * 查找折线图数据
	 * @param priorityId
	 * @param facilityId
	 * @param hostId
	 * @return
	 */
	@RequestMapping(value = "/findLineData", method = RequestMethod.POST)
	@ResponseBody
	public String findLineData(@RequestParam("priorityId")String priorityId,
			@RequestParam("facilityId")String facilityId, @RequestParam("hostId")String hostId){
		//查询最近一周，每天的日志数
		List<String> timeList = getLastWeekTime();
		List<SystemLogPicBean> list = new ArrayList<SystemLogPicBean>();
		for(int i=0;i<timeList.size();i++){
			String startTime = timeList.get(i)+" 00:00:00";
			String endTime = timeList.get(i)+" 59:59:59";
			List<SystemLogStatistics> logList = logStatisticsService.findLineData(priorityId, facilityId, hostId, startTime, endTime);
			int recordCount = 0;
			for (SystemLogStatistics systemLogStatistics : logList) {
				recordCount += systemLogStatistics.getRecords();
			}
			SystemLogPicBean bean = new SystemLogPicBean(recordCount, timeList.get(i));
			list.add(bean);
		}
		return JSONArray.fromObject(list).toString();
	}
	
	/**
	 * 获取最近一周，当前时间的前一天为一周最后一天
	 * @return
	 */
	private List<String> getLastWeekTime(){
		List<String> timeList = new ArrayList<String>();
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<7;i++){
			long time = DateUtil.getDayStart(new Date()).getTime()-24*3600*1000-24*3600*1000*(6-i);
			String strDate = sdf.format(time);
			timeList.add(strDate);
		}
		return timeList;
	}
}
