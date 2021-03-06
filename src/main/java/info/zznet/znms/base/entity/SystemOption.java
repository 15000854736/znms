package info.zznet.znms.base.entity;

import java.util.Date;

public class SystemOption {
	
    private String systemOptionUuid;

    private String systemOptionKey;

    private String systemOptionValue;

    private String systemOptionName;

    private Date createTime;

    private Date updateTime;

    public String getSystemOptionUuid() {
        return systemOptionUuid;
    }

    public void setSystemOptionUuid(String systemOptionUuid) {
        this.systemOptionUuid = systemOptionUuid;
    }

    public String getSystemOptionKey() {
        return systemOptionKey;
    }

    public void setSystemOptionKey(String systemOptionKey) {
        this.systemOptionKey = systemOptionKey;
    }

    public String getSystemOptionValue() {
        return systemOptionValue;
    }

    public void setSystemOptionValue(String systemOptionValue) {
        this.systemOptionValue = systemOptionValue;
    }

    public String getSystemOptionName() {
        return systemOptionName;
    }

    public void setSystemOptionName(String systemOptionName) {
        this.systemOptionName = systemOptionName;
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
}