/**
 * 
 */
package info.zznet.znms.web.module.system.service.impl;

import info.zznet.znms.base.dao.BackupConfigurationAccountPasswordMapper;
import info.zznet.znms.base.dao.BackupConfigurationDeviceMapper;
import info.zznet.znms.base.entity.BackupConfigurationAccountPassword;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.system.service.BackupAccountPasswordService;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dell001
 *
 */
@Service("accountPasswordService")
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
public class BackupAccountPasswordServiceImpl extends BaseServiceImpl implements BackupAccountPasswordService{

	@Autowired
	private BackupConfigurationAccountPasswordMapper accountPasswordMapper;
	
	@Autowired
	private BackupConfigurationDeviceMapper deviceMapper;

	@Override
	public Pager findPageList(Pager pager) {
		JSONArray searchCondition = null;
		if (pager != null && !StringUtil.isNullString(pager.getSearch())) {
			searchCondition = JSONArray.fromObject(pager.getSearch());
		}
		List<BackupConfigurationAccountPassword> list = accountPasswordMapper.findPageList(pager, searchCondition);
		pager.setRows(list);
		pager.setTotal(accountPasswordMapper.getCount(searchCondition));
		return pager;
	}

	@Override
	public BackupConfigurationAccountPassword findByApName(String apName,
			String accountPasswordUuid) {
		return accountPasswordMapper.findByApName(apName, accountPasswordUuid);
	}

	@Override
	public void addAccountPassword(
			BackupConfigurationAccountPassword accountPassword) {
		accountPassword.setAccountPasswordUuid(UUIDGenerator.getGUID());
		accountPassword.setCreateTime(new Date());
		accountPasswordMapper.insert(accountPassword);
	}

	@Override
	public BackupConfigurationAccountPassword findAPByUuid(
			String accountPasswordUuid) {
		return accountPasswordMapper.selectByPrimaryKey(accountPasswordUuid);
	}

	@Override
	public void updateAccountPassword(
			BackupConfigurationAccountPassword accountPassword) {
		accountPasswordMapper.updateByPrimaryKey(accountPassword);
	}

	@Override
	public void deleteByPrimaryKey(String uuid) {
		accountPasswordMapper.deleteByPrimaryKey(uuid);
		//删除引用该账户&密码的设备
		deviceMapper.deleteByAccountPasswordUuid(uuid);
	}

	@Override
	public String deleteByUuidList(List<String> uuids) {
		for (String uuid : uuids) {
			deleteByPrimaryKey(uuid);
		}
		return JSONObject.fromObject(findPageList(new Pager())).toString();
	}

	@Override
	public List<BackupConfigurationAccountPassword> findAll() {
		return accountPasswordMapper.findAll();
	}
}
