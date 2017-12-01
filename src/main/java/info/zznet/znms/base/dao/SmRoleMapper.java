package info.zznet.znms.base.dao;

import info.zznet.znms.base.entity.SmRole;
import info.zznet.znms.web.module.common.page.Pager;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;

public interface SmRoleMapper extends BaseMapper{
    int deleteByPrimaryKey(String roleUuid);

    int insert(SmRole record);

    int insertSelective(SmRole record);

    SmRole findByPrimaryKey(String roleUuid);

    int updateByPrimaryKeySelective(SmRole record);

    int updateByPrimaryKey(SmRole record);
    
    List<SmRole> findAll();

    /**
	 * 按角色名查找角色数量
	 * @param roleName
	 * @return
	 */
	int findCntByRoleName(@Param("roleName")String roleName);

	/**
	 * 按角色uuid查找管理员数
	 * @param roleUuid
	 * @return
	 */
	int findAdminCntByRole(@Param("roleUuid")String roleUuid);

	void deleteByUuidList(List<String> uuidList);
	
	List<SmRole> findPageList(@Param("pager")Pager pager, @Param("condition")JSONArray searchCondition);

	Long getCount(@Param("condition")JSONArray searchCondition);

}