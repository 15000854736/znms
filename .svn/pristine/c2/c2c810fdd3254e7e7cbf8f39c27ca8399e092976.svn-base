package info.zznet.znms.base.dao;

import info.zznet.znms.base.entity.ConfigBackupRecord;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.system.bean.DetailConfigBackupRecord;
import info.zznet.znms.web.module.system.bean.GroupedConfigBackupRecord;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("configBackupRecordMapper")
public interface ConfigBackupRecordMapper extends BaseMapper{
    int deleteByPrimaryKey(String id);

    int insert(ConfigBackupRecord record);

    ConfigBackupRecord selectByPrimaryKey(String id);
    
    /**
     * 按主机uuid查找
     * @param hostUuid
     * @return
     */
    List<ConfigBackupRecord> findByHost(@Param("hostUuid")String hostUuid);
    
    /**
     * 查找指定主机最新的成功记录
     * @param hostUuid
     * @return
     */
    ConfigBackupRecord findLatestSuccess(@Param("hostUuid")String hostUuid);
    
    /**
     * 查找分页数据(设备)
     * @param pager
     * @param hostUuid
     * @return
     */
    List<GroupedConfigBackupRecord> findPagedDataForDevice(@Param("pager")Pager pager, @Param("hostUuid")String hostUuid);
    long getRecordCountForDevice(@Param("hostUuid")String hostUuid);
    
    /**
     * 查找分页数据
     * @param pager
     * @param hostUuid
     * @return
     */
    List<DetailConfigBackupRecord> findPagedData(@Param("pager")Pager pager, @Param("hostUuid")String hostUuid);
    long getRecordCount(@Param("hostUuid")String hostUuid);
    
    DetailConfigBackupRecord findById(@Param("id")String id);
}