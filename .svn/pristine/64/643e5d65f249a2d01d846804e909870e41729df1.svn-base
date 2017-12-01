package info.zznet.znms.web.module.security.service;

import info.zznet.znms.base.entity.SmAdmin;
import info.zznet.znms.base.entity.SmAdmin;

import java.util.List;

/**
 * 登陆service类
 * @author yuanjingtao
 *
 */
public interface LoginService {
	
	/**
	 * 更新admin实体
	 * @param record 传入需要更新的entity
	 * @return
	 */
	int update(SmAdmin record);
	
	/**
	 * 通过用户名，密码，IP地址登陆
	 * @param name 管理员用户名
	 * @param password 管理员密码
	 * @param ip  IP地址
	 * @return
	 */
	public SmAdmin login(String name,String password,String ip);
	public boolean checkIpValid(String administratorUuid, String ip);
	public List<SmAdmin> findByUsername(String name);
	public SmAdmin findByUuid(String uuid);
	public String getRoleName(String roleId);
}
