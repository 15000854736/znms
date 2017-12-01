package info.zznet.znms.web.module.system.service;

import info.zznet.znms.base.entity.SmRole;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.BaseService;
import info.zznet.znms.base.entity.SmRole;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.BaseService;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface RolePermissionService<T1, T2> extends BaseService<T1, T2> {

	/**
	 * 按条件查找角色列表
	 * @param condition 查询条件
	 * @return
	 */
	public Pager findPageList(Pager pager);
	
	/**
	 * 按主键查找角色信息
	 * @param id
	 * @return
	 */
	public Map<String,Object> findById(String id); 
	
	/**
	 * 添加角色
	 * @param role 角色
	 * @param permissions 权限字符串
	 */
	public void addRole(SmRole role, String permissions);
	
	/**
	 * 更新角色
	 * @param role 角色
	 * @param permissions 权限字符串
	 */
	public void updateRole(SmRole role, String permissions);
	
	/**
	 * 按主键删除角色
	 */
	public void deleteByUuid(String uuid);
	
	/**
	 * 按主键列表删除角色
	 * @param uuidList
	 */
	void deleteByUuidList(List<String> uuidList);
	
	/**
	 * 按角色uuid查找管理员数
	 * @param roleUuid
	 */
	public int findAdminCntByRole(String roleUuid);
	
	/**
	 * 按角色名查找角色数
	 * @param roleName
	 * @return
	 */
	public int findCntByRoleName(String roleName);

	
}
