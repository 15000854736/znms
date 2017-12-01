package info.zznet.znms.base.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 阀值触发日志实体
 * @author dell001
 *
 */
public class ThresholdValueTriggerLog {
    private String logUuid;

    //主机uuid
    private String hostUuid;

    //阀值uuid
    private String thresholdValueUuid;

    //报警值
    private BigDecimal alarmValue;

    //当前值
    private BigDecimal currentValue;

    //描述
    private String description;

    //创建时间
    private Date createTime;
    
    public String getLogUuid() {
        return logUuid;
    }

    public void setLogUuid(String logUuid) {
        this.logUuid = logUuid;
    }

    public String getHostUuid() {
        return hostUuid;
    }

    public void setHostUuid(String hostUuid) {
        this.hostUuid = hostUuid;
    }

    public String getThresholdValueUuid() {
        return thresholdValueUuid;
    }

    public void setThresholdValueUuid(String thresholdValueUuid) {
        this.thresholdValueUuid = thresholdValueUuid;
    }

    public BigDecimal getAlarmValue() {
		return alarmValue;
	}

	public void setAlarmValue(BigDecimal alarmValue) {
		this.alarmValue = alarmValue;
	}

	public BigDecimal getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(BigDecimal currentValue) {
		this.currentValue = currentValue;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}