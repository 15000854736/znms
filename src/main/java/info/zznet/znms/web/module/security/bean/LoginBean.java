package info.zznet.znms.web.module.security.bean;

public class LoginBean {

    @SuppressWarnings("unused")
	private static final long serialVersionUID = -3386682283688735911L;
    private String loginName;
    private String password;
    private String validCode;

    /**
     * 获取管理员登录名
     * @return
     */
    public String getLoginName() {
        return loginName;
    }
    /**
     * 设置管理员登录名
     * @return
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    /**
     * 获取管理员登录密码
     * @return
     */
    public String getPassword() {
        return password;
    }
    /**
     * 设置管理员登录密码
     * @return
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * 获取验证码
     * @return
     */
    public String getValidCode() {
        return validCode;
    }
    /**
     * 设置验证码
     * @return
     */
    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

}
