package info.zznet.znms.base.entity;

import java.util.Date;

/**
 * 备份配置账户&密码实体
 * @author dell001
 *
 */
public class BackupConfigurationAccountPassword {
    private String accountPasswordUuid;

    //名称
    private String apName;

    //认证用户名
    private String certificateName;

    //密码
    private String password;

    //确认密码
    private String confirmPassword;

    //启用密码
    private String enablePassword;

    //确认启用密码
    private String confirmEnablePassword;

    //创建时间
    private Date createTime;

    public String getAccountPasswordUuid() {
        return accountPasswordUuid;
    }

    public void setAccountPasswordUuid(String accountPasswordUuid) {
        this.accountPasswordUuid = accountPasswordUuid;
    }

    public String getApName() {
        return apName;
    }

    public void setApName(String apName) {
        this.apName = apName;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEnablePassword() {
        return enablePassword;
    }

    public void setEnablePassword(String enablePassword) {
        this.enablePassword = enablePassword;
    }

    public String getConfirmEnablePassword() {
        return confirmEnablePassword;
    }

    public void setConfirmEnablePassword(String confirmEnablePassword) {
        this.confirmEnablePassword = confirmEnablePassword;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}