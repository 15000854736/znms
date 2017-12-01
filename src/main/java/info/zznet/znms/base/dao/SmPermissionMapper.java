package info.zznet.znms.base.dao;

import info.zznet.znms.base.entity.SmPermission;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SmPermissionMapper extends BaseMapper{
    int deleteByPrimaryKey(String permissionUuid);

    int insert(SmPermission record);

    int insertSelective(SmPermission record);

    SmPermission findByPrimaryKey(String permissionUuid);

    int updateByPrimaryKeySelective(SmPermission record);

    int updateByPrimaryKey(SmPermission record);
    
    /**
	 * 查找管理员拥有的权限
	 * @return
	 */
	List<SmPermission> findByRoleId(@Param("roleId")String roleId);
	
	/**
	 * 查找所有根权限
	 */
	List<SmPermission> findAllRoot();
}