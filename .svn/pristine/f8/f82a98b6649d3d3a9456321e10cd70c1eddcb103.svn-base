package info.zznet.znms.web.module.security.service.impl;

import info.zznet.znms.base.dao.SmRoleMapper;
import info.zznet.znms.base.dao.SmAdminMapper;
import info.zznet.znms.base.dao.SmRoleMapper;
import info.zznet.znms.base.entity.SmAdmin;
import info.zznet.znms.base.entity.SmRole;
import info.zznet.znms.web.module.security.service.LoginService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("LoginService")
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private SmAdminMapper<?, ?> smAdminMapper;
	@Autowired
	private SmRoleMapper smRoleMapper;
	
	@Override
	public int update(SmAdmin record){
		return smAdminMapper.updateByPrimaryKey(record);
	}
	
	@Override
	public SmAdmin login(String adminId, String password ,String ip) {
		SmAdmin result = null;
		List<SmAdmin> smAdmins = smAdminMapper.findByAdminId(adminId);
		if(smAdmins.size()>0){
			for(SmAdmin adminInfo : smAdmins){
				if(password.equals(adminInfo.getAdminPwd())){
					return adminInfo;
				}
			}
		}
		return result;
	}
	
	@Override
	public boolean checkIpValid(String administratorUuid, String ip) {
		return true;
	}
	
	

	@Override
	public List<SmAdmin> findByUsername(String adminId) {
		return smAdminMapper.findByAdminId(adminId);
	}
	
	@Override
	public SmAdmin findByUuid(String uuid){
		SmAdmin result = null;
		smAdminMapper.findByPrimaryKey(uuid);
		return result;
	}

	@Override
	public String getRoleName(String roleId) {
		SmRole role = smRoleMapper.findByPrimaryKey(roleId);
		return role != null?role.getRoleName():"";
	}

	
}
