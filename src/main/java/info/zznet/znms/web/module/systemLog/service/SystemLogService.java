/**
 * 
 */
package info.zznet.znms.web.module.systemLog.service;

import java.util.List;

import info.zznet.znms.base.entity.SystemLogStatistics;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.BaseService;

/**
 * @author dell001
 *
 */
public interface SystemLogService extends BaseService{

	/**
	 * @param pager
	 * @return
	 */
	Pager findPageList(Pager pager);

	/**
	 * @param seq
	 */
	void deleteByPrimaryKey(Long seq);

	/**
	 * @param uuids
	 */
	void deleteUuidList(List<Long> uuids);

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<SystemLogStatistics> findLogStatisticsList(String startDate,
			String endDate);

	/**
	 * @param emergyArray
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	long getNetHeathData(int[] emergyArray, String startDate, String endDate);

}
