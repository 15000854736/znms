/**
 * 
 */
package info.zznet.znms.web.module.thresholdValueAlarm.service;

import info.zznet.znms.base.entity.ThresholdValueTriggerLog;
import info.zznet.znms.base.entity.dto.SurveyDTO;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.BaseService;

import java.util.Date;
import java.util.List;

/**
 * @author dell001
 *
 */
public interface ThresholdValueLogService extends BaseService{

	/**
	 * @param pager
	 * @return
	 */
	Pager<ThresholdValueTriggerLog> findPageList(
			Pager<ThresholdValueTriggerLog> pager);

	/**
	 * @param logUuid
	 */
	void deleteByPrimaryKey(String logUuid);
	
	void deleteByThresHoldValueUUId(String thresHoldValueUuid);
	/**
	 * @param uuids
	 */
	void deleteByUuidList(List<String> uuids);

	/**
	 * @return
	 */
	List<SurveyDTO> findThresholdValueMap(Date startDate, Date endDate);

}
