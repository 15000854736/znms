package info.zznet.znms.base.dao;

import info.zznet.znms.base.entity.SmRRolePermission;
import info.zznet.znms.base.entity.SmRRolePermission;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SmRRolePermissionMapper extends BaseMapper{
    int deleteByPrimaryKey(String rRolePermissionUuid);

    int insert(SmRRolePermission record);

    int insertSelective(SmRRolePermission record);

    SmRRolePermission findByPrimaryKey(String rRolePermissionUuid);

    int updateByPrimaryKeySelective(SmRRolePermission record);

    int updateByPrimaryKey(SmRRolePermission record);

	void deleteByRoleId(String roleId);

    /**
     * 按角色ID列表删除映射
     * @param list
     */
    void deleteByRoleIdList(@Param("list")List<String> list);

    /**
     * 批量插入
     * @param list
     */
    void batchInsert(@Param("list")List<SmRRolePermission> list);
}

