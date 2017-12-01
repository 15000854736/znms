/**
 * 
 */
package info.zznet.znms.web.module.system.service.impl;

import info.zznet.znms.base.dao.BackupConfigurationDeviceMapper;
import info.zznet.znms.base.entity.BackupConfigurationDevice;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.system.service.BackupDeviceService;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dell001
 *
 */
@Service("deviceService")
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
public class BackupDeviceServiceImpl extends BaseServiceImpl implements BackupDeviceService{

	@Autowired
	private BackupConfigurationDeviceMapper deviceMapper;

	@Override
	public Pager<BackupConfigurationDevice> findPageList(Pager<BackupConfigurationDevice> pager) {
		JSONArray searchCondition = null;
		if (pager != null && !StringUtil.isNullString(pager.getSearch())) {
			searchCondition = JSONArray.fromObject(pager.getSearch());
		}
		List<BackupConfigurationDevice> list = deviceMapper.findPageList(pager, searchCondition);
		pager.setRows(list);
		pager.setTotal(deviceMapper.getCount(searchCondition));
		return pager;
	}

	@Override
	public void addDevice(BackupConfigurationDevice device) {
		device.setDeviceUuid(UUIDGenerator.getGUID());
		device.setCreateTime(new Date());
		deviceMapper.insert(device);
	}

	@Override
	public void update(BackupConfigurationDevice device) {
		deviceMapper.updateByPrimaryKey(device);
	}

	@Override
	public BackupConfigurationDevice selectByPrimaryKey(String deviceUuid) {
		return deviceMapper.selectByPrimaryKey(deviceUuid);
	}

	@Override
	public void deleteByPrimaryKey(String uuid) {
		deviceMapper.deleteByPrimaryKey(uuid);
	}

	@Override
	public void deleteByUuidList(List<String> uuids) {
		for (String uuid : uuids) {
			deleteByPrimaryKey(uuid);
		}
	}
}
