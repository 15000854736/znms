/**
 * 
 */
package info.zznet.znms.web.module.system.service;

import java.util.List;

import info.zznet.znms.base.entity.BackupConfigurationDevice;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.BaseService;

/**
 * @author dell001
 *
 */
public interface BackupDeviceService extends BaseService{

	/**
	 * @param pager
	 * @return
	 */
	Pager<BackupConfigurationDevice> findPageList(Pager<BackupConfigurationDevice> pager);

	/**
	 * @param device
	 */
	void addDevice(BackupConfigurationDevice device);

	/**
	 * @param device
	 */
	void update(BackupConfigurationDevice device);

	/**
	 * @param deviceUuid
	 * @return
	 */
	BackupConfigurationDevice selectByPrimaryKey(String deviceUuid);

	/**
	 * @param uuid
	 */
	void deleteByPrimaryKey(String uuid);

	/**
	 * @param uuids
	 */
	void deleteByUuidList(List<String> uuids);

}
