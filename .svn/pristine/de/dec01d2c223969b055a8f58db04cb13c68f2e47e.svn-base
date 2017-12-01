package info.zznet.znms.web.module.security.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface PermissionService {
	
	/**
	 * 按管理员信息（角色id）获取权限code列表
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true,rollbackFor=Exception.class)
	public List<String> getPermissionListByRole(String roleId);
}
