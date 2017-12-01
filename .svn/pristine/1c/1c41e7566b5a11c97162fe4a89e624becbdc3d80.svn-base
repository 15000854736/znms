/**
 * 
 */
package info.zznet.znms.web.module.system.service;

import java.util.List;

import info.zznet.znms.base.entity.BackupConfigurationAccountPassword;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.BaseService;

/**
 * @author dell001
 *
 */
public interface BackupAccountPasswordService extends BaseService{

	/**
	 * @param pager
	 * @return
	 */
	Pager findPageList(Pager pager);

	/**
	 * @param apName
	 * @param accountPasswordUuid
	 * @return
	 */
	BackupConfigurationAccountPassword findByApName(String apName, String accountPasswordUuid);

	/**
	 * @param accountPassword
	 */
	void addAccountPassword(BackupConfigurationAccountPassword accountPassword);

	/**
	 * @param accountPasswordUuid
	 * @return
	 */
	BackupConfigurationAccountPassword findAPByUuid(String accountPasswordUuid);

	/**
	 * @param accountPassword
	 */
	void updateAccountPassword(
			BackupConfigurationAccountPassword accountPassword);

	/**
	 * @param uuid
	 */
	void deleteByPrimaryKey(String uuid);

	/**
	 * @param uuids
	 * @return
	 */
	String deleteByUuidList(List<String> uuids);

	/**
	 * @return
	 */
	List<BackupConfigurationAccountPassword> findAll();

}
