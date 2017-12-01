/**
 * 
 */
package info.zznet.znms.web.module.apInformation.bean;

/**
 * @author dell001
 *星光图 搜索条件bean 
 */
public class DeviceSearchBean {
	
	//acIp
	private String acIp;

	//apName
	private String apName;
	
	//状态
	private Integer apPositionStatus;
	
	//区域uuid
	private String apRegionUuid;
	
	//主机名
	private String hostName;
	
	//主机ip
	private String hostIp;
	
	//主机类型  1.出口；2:核心；3：无线控制器；4.接入；5：汇聚; 6:其他
	private Integer hostType;
	
	//设备类型：  ap、host
	private String deviceType;
	

	public String getAcIp() {
		return acIp;
	}

	public void setAcIp(String acIp) {
		this.acIp = acIp;
	}

	public String getApName() {
		return apName;
	}

	public void setApName(String apName) {
		this.apName = apName;
	}

	public Integer getApPositionStatus() {
		return apPositionStatus;
	}

	public void setApPositionStatus(Integer apPositionStatus) {
		this.apPositionStatus = apPositionStatus;
	}

	public String getApRegionUuid() {
		return apRegionUuid;
	}

	public void setApRegionUuid(String apRegionUuid) {
		this.apRegionUuid = apRegionUuid;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public Integer getHostType() {
		return hostType;
	}

	public void setHostType(Integer hostType) {
		this.hostType = hostType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
}
