package info.zznet.znms.web.module.security.bean;

import info.zznet.znms.base.entity.SmAdmin;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.entity.SmAdmin;
import info.zznet.znms.base.util.StringUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Session Bean
 */
public class SessionBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1296413682345625364L;
	// 管理员信息
	private SmAdmin smAdmin;
	// 管理员角色
	private String roleName;
	// 管理员ip
	private String host;
	//管理员登陆时间
    private String loginTime;
	// 权限(编号)信息
	private List<String> permissionList;
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 获取用户登录信息的ip地址信息
	 * @return
	 */
	public String getHost() {
		return host;
	}
	/**
	 * 设置用户登录信息的ip地址信息
	 * @param host IP地址
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * 获取用户登录信息的登陆时间信息
	 * @return
	 */
	public String getLoginTime() {
		return loginTime;
	}
	
	/**
	 * 设置用户登录信息的登陆时间信息
	 * @param loginTime 用户登录信息的登陆时间信息
	 */
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public List<String> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<String> permissionList) {
		this.permissionList = permissionList;
	}
	
	/**
	 * 判断是否拥有指定权限
	 * @param permissionExpression 权限表达式
	 * @return
	 */
	public Boolean hasPermission(String permissionExpression){
		if(StringUtil.isNullString(permissionExpression)){
			return true;
		}
		String[] permissions = permissionExpression.split("\\|\\|");
		for(String permission : permissions){
			if(permissionList.contains(permission.trim())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否是超级管理员
	 * @param permissionExpression 权限表达式
	 * @return
	 */
	public Boolean isAdministrator(){
		return roleName.equals("超级管理员");
	}

	public SmAdmin getSmAdmin() {
		return smAdmin;
	}

	public void setSmAdmin(SmAdmin smAdmin) {
		this.smAdmin = smAdmin;
	}
}
