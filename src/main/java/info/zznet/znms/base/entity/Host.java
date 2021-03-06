package info.zznet.znms.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 主机实体类
 * @author dell001
 *
 */
public class Host implements Serializable{
	
	private static final long serialVersionUID = 5402734939067816811L;
	
	public static final int[] MAIN_DEVICE_TYPE = {1,2,3};
	
	//2、4、5为交换设备；1为网关设备；3为无线网关，6为其他设备
	public static final int[] EXCHANGE_DEVICE_TYPE ={2,4,5};
	
	public static final int[] GATEWAY_DEVICE_TYPE ={1};
	
	public static final int[] WIRELESS_DEVICE_TYPE ={3};
	
	public static final int[] OTHER_DEVICE_TYPE ={6};

	//主键
    private String id;
    
	//主机ip
    private String hostIp;

    //主机名称
    private String hostName;
    
    //snmp团体名
    private String snmpCommunity;

    //snmp版本
    private Integer snmpVersion;

    //snmp v3版本用户名
    private String snmpUserName;

    //snmp v3版本密码
    private String snmpPassword;

  //snmp v3版本验证协议
    private String snmpAuthProtocol;
    
  //snmp v3版本私有密码短语
    private String snmpPrivPassphrase;

  //snmp v3版本私有协议
    private String snmpPrivProtocol;

  //snmp v3版本上下文
    private String snmpContext;

  //snmp选项  端口
    private Integer snmpPort;

  //snmp选项  超时
    private Integer snmpTimeout;

  //可用性选项  检测方法     0:无;1:SNMP;2:Ping
    private Short availabilityMethod;

  //可选性选项 包类型
    private Short pingMethod;

  //可选性选项  检测端口
    private Integer pingPort;

  //可选性选项   检测超时
    private Integer pingTimeout;

  //可选性选项  重测次数
    private Integer pingRetries;

  //主机是否禁用
    private Byte status;

  //主机禁用日期
    private Date statusFailDate;

	//主机启用日期
    private Date statusRecDate;

  //最近一次报错时间
    private String statusLastError;

    private BigDecimal minTime;

    private BigDecimal maxTime;

    //当前延迟（毫秒）
    private BigDecimal curTime;

    //平均延时（毫秒）
    private BigDecimal avgTime;

    //可用性（百分比）
    private BigDecimal availability;
    
    //主机工作状态    1:正常；2:宕机
    private int hostWorkStatus;
    
    //最后宕机时间
    private Date lastShutDownTime;

  //额外选项 说明
    private String notes;
    
    private Date CreateTime;
    
    //主机类型  1.出口；2:核心；3：无线控制器；4.接入；5：汇聚; 6:其他
    private int type;
    
    //主机cpu使用率  首页显示用
    private String cpuUsePercent;
    
    //主机内存使用率   首页显示用
    private String memoryUsePercent;
    
    //用来区分设备
    private String acBrand="RUIJIE";
    
    //区域uuid
    private String apRegionUuid;
    
    //主机在地图上的坐标
    private String hostAxis;
    
    public String getAcBrand() {
		return acBrand;
	}

	public void setAcBrand(String acBrand) {
		this.acBrand = acBrand;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSnmpCommunity() {
        return snmpCommunity;
    }

    public void setSnmpCommunity(String snmpCommunity) {
        this.snmpCommunity = snmpCommunity;
    }

    public Integer getSnmpVersion() {
        return snmpVersion;
    }

    public void setSnmpVersion(Integer snmpVersion) {
        this.snmpVersion = snmpVersion;
    }

    public String getSnmpUserName() {
        return snmpUserName;
    }

    public void setSnmpUserName(String snmpUserName) {
        this.snmpUserName = snmpUserName;
    }

    public String getSnmpPassword() {
        return snmpPassword;
    }

    public void setSnmpPassword(String snmpPassword) {
        this.snmpPassword = snmpPassword;
    }

    public String getSnmpAuthProtocol() {
        return snmpAuthProtocol;
    }

    public void setSnmpAuthProtocol(String snmpAuthProtocol) {
        this.snmpAuthProtocol = snmpAuthProtocol;
    }

    public String getSnmpPrivPassphrase() {
        return snmpPrivPassphrase;
    }

    public void setSnmpPrivPassphrase(String snmpPrivPassphrase) {
        this.snmpPrivPassphrase = snmpPrivPassphrase;
    }

    public String getSnmpPrivProtocol() {
        return snmpPrivProtocol;
    }

    public void setSnmpPrivProtocol(String snmpPrivProtocol) {
        this.snmpPrivProtocol = snmpPrivProtocol;
    }

    public String getSnmpContext() {
        return snmpContext;
    }

    public void setSnmpContext(String snmpContext) {
        this.snmpContext = snmpContext;
    }

    public Integer getSnmpPort() {
        return snmpPort;
    }

    public void setSnmpPort(Integer snmpPort) {
        this.snmpPort = snmpPort;
    }

    public Integer getSnmpTimeout() {
        return snmpTimeout;
    }

    public void setSnmpTimeout(Integer snmpTimeout) {
        this.snmpTimeout = snmpTimeout;
    }

    public Short getAvailabilityMethod() {
        return availabilityMethod;
    }

    public void setAvailabilityMethod(Short availabilityMethod) {
        this.availabilityMethod = availabilityMethod;
    }

    public Short getPingMethod() {
        return pingMethod;
    }

    public void setPingMethod(Short pingMethod) {
        this.pingMethod = pingMethod;
    }

    public Integer getPingPort() {
        return pingPort;
    }

    public void setPingPort(Integer pingPort) {
        this.pingPort = pingPort;
    }

    public Integer getPingTimeout() {
        return pingTimeout;
    }

    public void setPingTimeout(Integer pingTimeout) {
        this.pingTimeout = pingTimeout;
    }

    public Integer getPingRetries() {
        return pingRetries;
    }

    public void setPingRetries(Integer pingRetries) {
        this.pingRetries = pingRetries;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getStatusFailDate() {
        return statusFailDate;
    }

    public void setStatusFailDate(Date statusFailDate) {
        this.statusFailDate = statusFailDate;
    }

    public Date getStatusRecDate() {
        return statusRecDate;
    }

    public void setStatusRecDate(Date statusRecDate) {
        this.statusRecDate = statusRecDate;
    }

    public String getStatusLastError() {
        return statusLastError;
    }

    public void setStatusLastError(String statusLastError) {
        this.statusLastError = statusLastError;
    }

    public BigDecimal getMinTime() {
        return minTime;
    }

    public void setMinTime(BigDecimal minTime) {
        this.minTime = minTime;
    }

    public BigDecimal getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(BigDecimal maxTime) {
        this.maxTime = maxTime;
    }

    public BigDecimal getCurTime() {
        return curTime;
    }

    public void setCurTime(BigDecimal curTime) {
        this.curTime = curTime;
    }

    public BigDecimal getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(BigDecimal avgTime) {
        this.avgTime = avgTime;
    }

    public BigDecimal getAvailability() {
        return availability;
    }

    public void setAvailability(BigDecimal availability) {
        this.availability = availability;
    }
    
    public int getHostWorkStatus() {
		return hostWorkStatus;
	}

	public void setHostWorkStatus(int hostWorkStatus) {
		this.hostWorkStatus = hostWorkStatus;
	}

	public Date getLastShutDownTime() {
		return lastShutDownTime;
	}

	public void setLastShutDownTime(Date lastShutDownTime) {
		this.lastShutDownTime = lastShutDownTime;
	}

	public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCpuUsePercent() {
		return cpuUsePercent;
	}

	public void setCpuUsePercent(String cpuUsePercent) {
		this.cpuUsePercent = cpuUsePercent;
	}

	public String getMemoryUsePercent() {
		return memoryUsePercent;
	}

	public void setMemoryUsePercent(String memoryUsePercent) {
		this.memoryUsePercent = memoryUsePercent;
	}
	
	public String getApRegionUuid() {
		return apRegionUuid;
	}

	public void setApRegionUuid(String apRegionUuid) {
		this.apRegionUuid = apRegionUuid;
	}

	public String getHostAxis() {
		return hostAxis;
	}

	public void setHostAxis(String hostAxis) {
		this.hostAxis = hostAxis;
	}

	@Override
	public String toString() {
		return hostName;
	}
    
	
}