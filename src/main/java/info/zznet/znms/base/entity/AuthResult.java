package info.zznet.znms.base.entity;

import java.util.Date;

public class AuthResult {
    private String authResultUuid;

    private String serialNumber;

    private String returnMsg;

    private Byte resultType;

    private Date expiryDate;

    private Date createTime;

    private Date updateTime;

    private String createHost;

    private String updateHost;

    public String getAuthResultUuid() {
        return authResultUuid;
    }

    public void setAuthResultUuid(String authResultUuid) {
        this.authResultUuid = authResultUuid;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Byte getResultType() {
        return resultType;
    }

    public void setResultType(Byte resultType) {
        this.resultType = resultType;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateHost() {
        return createHost;
    }

    public void setCreateHost(String createHost) {
        this.createHost = createHost;
    }

    public String getUpdateHost() {
        return updateHost;
    }

    public void setUpdateHost(String updateHost) {
        this.updateHost = updateHost;
    }
}