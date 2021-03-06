/**
 * 
 */
package info.zznet.znms.web.module.system.service;

import info.zznet.znms.base.entity.Host;
import info.zznet.znms.web.module.apInformation.bean.DeviceSearchBean;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.BaseService;

import java.util.List;

/**
 * @author dell001
 *
 */
public interface HostService extends BaseService{

	/**
	 * @param pager
	 * @return
	 */
	Pager<Host> findPageList(Pager<Host> pager);

	/**
	 * @param host
	 */
	void update(Host host);

	/**
	 * @param host
	 */
	void addHost(Host host);

	/**
	 * @param id
	 * @return
	 */
	Host findHostById(String id);

	/**
	 * @param id
	 */
	void deleteByPrimaryKey(String id);

	/**
	 * @param uuids
	 * @return
	 */
	String deleteByUuidList(List<String> uuids);

	/**
	 * 批量启用主机
	 * @param uuidList
	 */
	void updateHostEnable(List<String> uuidList);

	/**
	 * 批量禁用主机
	 * @param uuidList
	 */
	void updateHostDisable(List<String> uuidList);

	/**
	 * 查找所有可用主句
	 * @param nodeUuid
	 * @return
	 */
	List<Host> findApplicableHost(String nodeUuid);

	/**
	 * @param deviceUuid
	 * @return
	 */
	List<Host> findDeviceHost(String deviceUuid);

	/**
	 * @return
	 */
	List<Host> findAll();

	/**
	 * @param host
	 * @return
	 */
	Host checkHostIp(Host host);

	/**
	 * 判断主机名是否重复
	 * @param host
	 * @return
	 */
	Host checkHostName(Host host);

	/**
	 * @param ip
	 * @param isReachable
	 */
	void updateHostWorkStatusByIP(String ip, boolean isReachable);

	/**
	 * 查找所有接入为ac的主机
	 * @return
	 */
	List<Host> findAllAc();

	/**
	 * @param deviceTypeArray
	 * @param hostWorkStatus
	 * @return
	 */
	int getDeviceCountByCondition(int[] deviceTypeArray, int hostWorkStatus);

	/**
	 * @param defaultAccessArray
	 * @return
	 */
	List<Host> getMainDeviceMonitorData(int[] defaultAccessArray);
	
	List<Host> getShutDownHost(int hostWorkStatus);

	/**
	 * @param bean
	 * @return
	 */
	List findHostByCondition(DeviceSearchBean bean);

	/**
	 * @param split
	 * @param apAxis
	 */
	void updateHostAxis(String[] split, String apAxis);

	/**
	 * @param split
	 * @param apRegionUuid
	 */
	void updateHostAxisByRegion(String[] split, String apRegionUuid);
	
}
