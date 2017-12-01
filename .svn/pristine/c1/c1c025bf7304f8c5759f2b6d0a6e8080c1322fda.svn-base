package info.zznet.znms.base.entity;

import info.zznet.znms.web.annotation.PropKey;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志实体类
 * @author dell001
 *
 */
public class SystemLog implements Serializable{
	
	private static final long serialVersionUID = 5402734939067816805L;
	
	//日志自增长主键
    private Long seq;

    /**
     * 日志类型
     */
    @PropKey("systemLog.entity.facility")
    private Integer facilityId;
    
    private String facilityName;

    /**
     * 日志级别
     */
    @PropKey("systemLog.entity.priority")
    private Integer priorityId;
    
    private String priorityName;

    //主机id
    private String hostId;

    //日志生成时间
    @PropKey("systemLog.entity.logTime")
    private Date logTime;
    
    private String logTimeStr;

    //消息
    @PropKey("systemLog.entity.message")
    private String message;
    
    @PropKey("systemLog.entity.host")
    private Host host;
    
    private String hostIp;
    
    private String hostName;

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public String getPriorityName() {
		return priorityName;
	}

	public void setPriorityName(String priorityName) {
		this.priorityName = priorityName;
	}

	public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getMessage() {
        return message;
    }

    public String getLogTimeStr() {
		return logTimeStr;
	}

	public void setLogTimeStr(String logTimeStr) {
		this.logTimeStr = logTimeStr;
	}

	public void setMessage(String message) {
        this.message = message;
    }

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
    
}