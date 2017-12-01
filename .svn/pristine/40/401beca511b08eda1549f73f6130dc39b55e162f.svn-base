package info.zznet.znms.web.module.security.service.impl;

import info.zznet.znms.base.dao.SmPermissionMapper;
import info.zznet.znms.base.entity.SmPermission;
import info.zznet.znms.web.module.security.service.PermissionService;
import info.zznet.znms.base.dao.SmPermissionMapper;
import info.zznet.znms.base.entity.SmPermission;
import info.zznet.znms.web.module.security.service.PermissionService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private SmPermissionMapper permissionMapper;

	@Override
	public List<String> getPermissionListByRole(String roleId) {
		List<SmPermission> rootPermissions = permissionMapper.findByRoleId(roleId);
		List<String> leafPermissionCodes = new ArrayList<String>();
		for(SmPermission rootPermission : rootPermissions){
			exactRootPermission(leafPermissionCodes, rootPermission);
		}
		return leafPermissionCodes;
	}
	
	// 提取叶节点权限
	private void exactRootPermission(List<String> leafPermissionCodes, SmPermission rootPermission){
		List<SmPermission> childPermissions = rootPermission.getChildPermissionList();
		if(childPermissions != null && !childPermissions.isEmpty()){
			leafPermissionCodes.add(rootPermission.getPermissionCode());
			for(SmPermission childPermission:childPermissions){
				exactRootPermission(leafPermissionCodes, childPermission);				
			}
		} else {
			leafPermissionCodes.add(rootPermission.getPermissionCode());
		}
	}	
}
