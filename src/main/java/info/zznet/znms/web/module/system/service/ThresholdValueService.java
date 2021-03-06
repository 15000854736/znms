/**
 * 
 */
package info.zznet.znms.web.module.system.service;

import java.util.List;
import java.util.Map;

import info.zznet.znms.base.entity.ThresholdValue;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.BaseService;

/**
 * @author dell001
 *
 */
public interface ThresholdValueService extends BaseService{

	/**
	 * @param pager
	 * @return
	 */
	Pager<ThresholdValue> findPageList(Pager<ThresholdValue> pager);
	
	List<ThresholdValue> findByTime(String startDate,
			String endDate);

	/**
	 * @param thresholdValue
	 */
	String add(ThresholdValue thresholdValue);

	/**
	 * @param thresholdValue
	 */
	void update(ThresholdValue thresholdValue);

	/**
	 * @param thresholdValueUuid
	 * @return
	 */
	ThresholdValue selectByPrimaryKey(String thresholdValueUuid);

	/**
	 * @param thresholdValueUuid
	 */
	void deleteByPrimaryKey(String thresholdValueUuid);

	/**
	 * @param uuids
	 */
	void deleteThresholdValueList(List<String> uuids);

	/**
	 * @param uuidList
	 */
	void updateThresholdValueEnable(List<String> uuidList);

	/**
	 * @param uuidList
	 */
	void updateThresholdValueDisable(List<String> uuidList);

	/**
	 * @return
	 */
	List<ThresholdValue> findAll();

	/**
	 * @param thresholdValueUuid
	 * @param thresholdValueName
	 * @return
	 */
	ThresholdValue checkThresholdValueName(String thresholdValueUuid,
			String thresholdValueName);

}
