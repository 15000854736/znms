package info.zznet.znms.web.module.system.service.impl;

import info.zznet.znms.base.dao.SmPermissionMapper;
import info.zznet.znms.base.dao.SmRRolePermissionMapper;
import info.zznet.znms.base.dao.SmRoleMapper;
import info.zznet.znms.base.entity.SmPermission;
import info.zznet.znms.base.entity.SmRRolePermission;
import info.zznet.znms.base.entity.SmRole;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.system.service.RolePermissionService;
import info.zznet.znms.web.util.BeanHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("rolePermissionService")
public class RolePermissionServiceImpl <T1, T2> extends BaseServiceImpl<T1, T2> implements RolePermissionService<T1, T2> {

	@Autowired
	private SmRoleMapper roleMapper;
	@Autowired
	private SmRRolePermissionMapper rRolePermissionMapper;
	@Autowired
	private SmPermissionMapper permissionMapper;
	
	@Override
	public Pager findPageList(Pager pager) {
		JSONArray searchCondition = null;
		if(!StringUtil.isNullString(pager.getSearch())){
			searchCondition = JSONArray.fromObject(pager.getSearch());
		}
		List<SmRole> roleList = roleMapper.findPageList(pager, searchCondition);
		pager.setRows(roleList);
		pager.setTotal(roleMapper.getCount(searchCondition));
		return pager;
	}
	
	@Override
	public Map<String,Object> findById(String id) {
		SmRole role = roleMapper.findByPrimaryKey(id);
		List<SmPermission> permissionList = permissionMapper.findByRoleId(role.getRoleUuid());
		StringBuffer permissions = new StringBuffer(2000);
		if(permissionList != null){
			for(SmPermission permission:permissionList){
				permissions.append(",").append(permission.getPermissionUuid());
			}
		}
		Map<String,Object> roleForShow = BeanHelper.convertBeanToMap(role);
		roleForShow.put("permissions", permissions.toString());
		return roleForShow;
	}

	@Override
	public void addRole(SmRole role, String permissions) {
		roleMapper.insert(role);
		setPermission(permissions, role.getRoleUuid());
	}
	
	@Override
	public void updateRole(SmRole role, String permissions) {
		roleMapper.updateByPrimaryKey(role);
		setPermission(permissions, role.getRoleUuid());
	}
	
	@Override
	public void deleteByUuid(String uuid) {
		roleMapper.deleteByPrimaryKey(uuid);
		rRolePermissionMapper.deleteByRoleId(uuid);
	}

	@Override
	public void deleteByUuidList(List<String> uuidList) {
		roleMapper.deleteByUuidList(uuidList);
		rRolePermissionMapper.deleteByRoleIdList(uuidList);
	}

	@Override
	public int findAdminCntByRole(String roleUuid) {
		return roleMapper.findAdminCntByRole(roleUuid);
	}
	
	/**
	 * 设置权限
	 * @param permissionIdList
	 * @param roleId
	 */
	private void setPermission(String permissions, String roleId) {
		rRolePermissionMapper.deleteByRoleId(roleId);
		String[] permissionArr = permissions.split(",");
		List<SmRRolePermission> mapList = new ArrayList<SmRRolePermission>();
		for(String permission : permissionArr){
			if(!StringUtil.isNullString(permission)){
				mapList.add(new SmRRolePermission(UUID.randomUUID().toString().replace("-", ""), roleId, permission));
			}
		}
		if(!mapList.isEmpty()){
			rRolePermissionMapper.batchInsert(mapList);
		}
	}

	@Override
	public int findCntByRoleName(String roleName) {
		return roleMapper.findCntByRoleName(roleName);
	}
}
