package info.zznet.znms.base.dao;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.BackupConfigurationAccountPassword;
import info.zznet.znms.web.module.common.page.Pager;

@Repository("accountPasswordMapper")
public interface BackupConfigurationAccountPasswordMapper extends BaseMapper{
    int deleteByPrimaryKey(String accountPasswordUuid);

    int insert(BackupConfigurationAccountPassword record);

    int insertSelective(BackupConfigurationAccountPassword record);

    BackupConfigurationAccountPassword selectByPrimaryKey(String accountPasswordUuid);

    int updateByPrimaryKeySelective(BackupConfigurationAccountPassword record);

    int updateByPrimaryKey(BackupConfigurationAccountPassword record);

	/**
	 * @param pager
	 * @param condition
	 * @return
	 */
	List<BackupConfigurationAccountPassword> findPageList(@Param("pager")Pager pager, 
			@Param("condition")JSONArray searchCondition);

	/**
	 * @param condition
	 * @return
	 */
	Long getCount(@Param("condition")JSONArray searchCondition);

	/**
	 * 根据名称和uuid查找账户密码
	 * @param apName
	 * @param accountPasswordUuid
	 * @return
	 */
	BackupConfigurationAccountPassword findByApName(@Param("apName")String apName,
			@Param("accountPasswordUuid")String accountPasswordUuid);

	/**
	 * @return
	 */
	List<BackupConfigurationAccountPassword> findAll();
}