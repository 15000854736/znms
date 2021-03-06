/**
 * 
 */
package info.zznet.znms.web.module.thresholdValueAlarm.service;

import java.util.List;

import info.zznet.znms.base.entity.ThresholdValueTriggerSurvey;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.BaseService;

/**
 * @author dell001
 *
 */
public interface ThresholdValueTriggerSurveyService extends BaseService{

	/**
	 * @param pager
	 * @return
	 */
	Pager<ThresholdValueTriggerSurvey> findPageList(
			Pager<ThresholdValueTriggerSurvey> pager);

	/**
	 * @param surveyUuid
	 */
	void deleteByPrimaryKey(String surveyUuid);

	/**
	 * @param uuids
	 */
	void deleteByUuidList(List<String> uuids);
	void deleteByThresHoldValueUUId(String thresHoldValueUuid);
	
	/**
	 * @param thresholdValueUuid
	 * @return
	 */
	ThresholdValueTriggerSurvey findByThresholdValueUuid(String thresholdValueUuid);

	/**
	 * @param tvts
	 */
	void add(ThresholdValueTriggerSurvey tvts);

	/**
	 * @param survey
	 */
	void update(ThresholdValueTriggerSurvey survey);

}
