/**
 * 
 */
package info.zznet.znms.web.module.security.service;

import java.util.List;

import info.zznet.znms.base.entity.Host;
import info.zznet.znms.web.module.common.service.BaseService;

/**
 * @author dell001
 *
 */
public interface HomePageService extends BaseService{

	/**
	 * @param startTime
	 * @param endTime
	 * @param wireType
	 * @return
	 */
	Integer findUserCountByCondition(String startTime, String endTime, Integer wireType);

	/**
	 * @return 
	 * 
	 */
	List<List<Integer>> getDeviceData();

	/**
	 * @return
	 */
	List<Host> getMainDeviceMonitorData();

	/**
	 * @return
	 */
	List<Long> getNetHeathData();

}
