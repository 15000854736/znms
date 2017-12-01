/**
 * 
 */
package info.zznet.znms.base.job;

import info.zznet.znms.base.dao.ThresholdValueMapper;
import info.zznet.znms.base.entity.ThresholdValueTriggerSurvey;
import info.zznet.znms.base.entity.dto.SurveyDTO;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.module.thresholdValueAlarm.service.ThresholdValueLogService;
import info.zznet.znms.web.module.thresholdValueAlarm.service.ThresholdValueTriggerSurveyService;
import info.zznet.znms.web.start.ThresholdValueThread;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 阀值触发任务
 * @author dell001
 *
 */
@Service
@DisallowConcurrentExecution
public class ThresholdValueTriggerJob {
	
	@Autowired
	private ThresholdValueLogService thresholdValueLogService;
	
	@Autowired
	private ThresholdValueTriggerSurveyService surveyService;
	
	@Autowired
	private ThresholdValueMapper thresholdValueMapper;

	@Scheduled(fixedDelay = 5*60*1000)
	public void triggerThresholdValue(){
		//查询最近一小时阀值触发次数
		Date hourStart = new Date(System.currentTimeMillis()-60*60*1000);
		Date hourEnd = new Date(System.currentTimeMillis());
		List<SurveyDTO> hourList = thresholdValueLogService.findThresholdValueMap(hourStart, hourEnd);
		Map<String,Integer> hourMap = convertListToMap(hourList);
		//查询最近一天阀值触发次数
		Date dayStart = new Date(System.currentTimeMillis()-24*60*60*1000);
		Date dayEnd = new Date(System.currentTimeMillis());
		List<SurveyDTO> dayList = thresholdValueLogService.findThresholdValueMap(dayStart ,dayEnd);
		Map<String,Integer> dayMap = convertListToMap(dayList);
		//查询最近一周阀值触发次数
		Date weekStart = new Date(System.currentTimeMillis()-7*24*60*60*1000);
		Date weekEnd = new Date(System.currentTimeMillis());
		List<SurveyDTO> weekList = thresholdValueLogService.findThresholdValueMap(weekStart, weekEnd);
		Map<String,Integer> weekMap = convertListToMap(weekList);
		for (String thresholdValueUuid : weekMap.keySet()) {
			ThresholdValueTriggerSurvey survey = surveyService.findByThresholdValueUuid(thresholdValueUuid);
			if(null==survey){
				ThresholdValueTriggerSurvey tvts = new ThresholdValueTriggerSurvey();
				tvts.setSurveyUuid(UUIDGenerator.getGUID());
				tvts.setThresholdValueUuid(thresholdValueUuid);
				tvts.setLastHourTriggerCount(null==hourMap.get(thresholdValueUuid) ?0:hourMap.get(thresholdValueUuid));
				tvts.setLastDayTriggerCount(null==dayMap.get(thresholdValueUuid) ?0:dayMap.get(thresholdValueUuid));
				tvts.setLastWeekTriggerCount(weekMap.get(thresholdValueUuid));
				surveyService.add(tvts);
			}else{
				survey.setLastHourTriggerCount(null==hourMap.get(thresholdValueUuid) ?0:hourMap.get(thresholdValueUuid));
				survey.setLastDayTriggerCount(null==dayMap.get(thresholdValueUuid) ?0:dayMap.get(thresholdValueUuid));
				survey.setLastWeekTriggerCount(weekMap.get(thresholdValueUuid));
				surveyService.update(survey);
			}
		}
			
	}
	
	private Map<String,Integer> convertListToMap(List<SurveyDTO> list){
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		if(null!=list && list.size()>0){
			for (SurveyDTO surveyDTO : list) {
				resultMap.put(surveyDTO.getThresholdValueUuid(), surveyDTO.getCount());
			}
		}
		return resultMap;
	}
	
	/**
	 * 定期维护图形阀值map
	 */
	@Scheduled(fixedDelay = 60*1000)
	public void updateGraphThresholdValueMap(){
		ThresholdValueThread.updateGraphThresholdValueMap(thresholdValueMapper);
	}
}
