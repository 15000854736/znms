package info.zznet.znms.base.dao;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.BackupConfigurationDevice;
import info.zznet.znms.web.module.common.page.Pager;

@Repository("deviceMapper")
public interface BackupConfigurationDeviceMapper extends BaseMapper{
    int deleteByPrimaryKey(String deviceUuid);

    int insert(BackupConfigurationDevice record);

    int insertSelective(BackupConfigurationDevice record);

    BackupConfigurationDevice selectByPrimaryKey(String deviceUuid);

    int updateByPrimaryKeySelective(BackupConfigurationDevice record);

    int updateByPrimaryKey(BackupConfigurationDevice record);

	/**
	 * @param pager
	 * @param searchCondition
	 * @return
	 */
	List<BackupConfigurationDevice> findPageList(@Param("pager")Pager pager,
			@Param("condition")JSONArray searchCondition);

	/**
	 * @param searchCondition
	 * @return
	 */
	Long getCount(@Param("condition")JSONArray searchCondition);

	/**
	 * 根据主机uuid删除设备
	 * @param hostUuid
	 */
	void deleteByHostUuid(@Param("hostUuid")String hostUuid);

	/**
	 * 根据账户&密码uuid删除设备
	 * @param accountPasswordUuid
	 */
	void deleteByAccountPasswordUuid(@Param("accountPasswordUuid")String accountPasswordUuid);

	BackupConfigurationDevice findByHostId(@Param("hostId")String hostId);
}