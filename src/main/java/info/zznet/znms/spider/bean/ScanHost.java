package info.zznet.znms.spider.bean;

import java.util.List;

/**
 * 扫描主机相关信息
 * Created by shenqilei on 2016/9/21.
 */
public class ScanHost {

    /**
     * 主机名
     */
    private String name;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 状态，启用/禁用
     */
    private String status;


    /**
     * snmp版本号
     */
    private String snmpVersion;

    /**
     * snmp团体名
     */
    private String community;


    /**
     * snmp v3版本 用户名
     */
    private String snmpUserName;

    /**
     * snmp v3 版本密码
     */
    private String snmpPassword;

    /**
     * snmp v3 认证协议
     */
    private String snmpAuthProtocol;


    private String snmpPrivPassphrase;


    private String snmpPrivProtocol;

    /**
     * snmp v3 上下文
     */
    private String snmpContext;

    /**
     * snmp 端口
     */
    private String snmpPort;

    /**
     * 扫描项
     */
    private List<ScanItem> scanItems;

    /**
     * 主机可达状态
     */
    private boolean isReachable;
    
    /**
     * 可用性选项  检测方法     0:无;1:SNMP;2:Ping
     */
    private Short availabilityMethod;
    
    //主机类型：1.出口；2:核心；3：无线控制器；4.接入；5：汇聚; 6:其他
    private int type;
    
    //主机坐标
    private String hostAxis;
    
    //主机区域uuid
    private String apRegionUuid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSnmpVersion() {
        return snmpVersion;
    }

    public void setSnmpVersion(String snmpVersion) {
        this.snmpVersion = snmpVersion;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
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

    public String getSnmpPort() {
        return snmpPort;
    }

    public void setSnmpPort(String snmpPort) {
        this.snmpPort = snmpPort;
    }

    public List<ScanItem> getScanItems() {
        return scanItems;
    }

    public void setScanItems(List<ScanItem> scanItems) {
        this.scanItems = scanItems;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isReachable() {
        return isReachable;
    }

    public void setReachable(boolean reachable) {
        isReachable = reachable;
    }
    
    public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String getHostAxis() {
		return hostAxis;
	}

	public void setHostAxis(String hostAxis) {
		this.hostAxis = hostAxis;
	}

	public String getApRegionUuid() {
		return apRegionUuid;
	}

	public void setApRegionUuid(String apRegionUuid) {
		this.apRegionUuid = apRegionUuid;
	}

	@Override
    public String toString() {
        return ip +" - " + name;
    }

	public Short getAvailabilityMethod() {
		return availabilityMethod;
	}

	public void setAvailabilityMethod(Short availabilityMethod) {
		this.availabilityMethod = availabilityMethod;
	}
}
