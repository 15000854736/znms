/**
 * 
 */
package info.zznet.znms.web.module.systemLog.service;

import java.util.List;

import info.zznet.znms.base.entity.SystemLogStatistics;
import info.zznet.znms.web.module.common.service.BaseService;

/**
 * @author dell001
 *
 */
public interface SystemLogStatisticsService extends BaseService{

	/**
	 * @param id
	 * @param hostId
	 * @param insertTimeFrom
	 * @param insertTimeTo
	 * @param type
	 * @return
	 */
	List<SystemLogStatistics> findPieData(String id,
			String hostId, String insertTimeFrom, String insertTimeTo, Integer type);

	/**
	 * @param priorityId
	 * @param facilityId
	 * @param hostId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<SystemLogStatistics> findLineData(String priorityId,
			String facilityId, String hostId, String startTime, String endTime);

}
