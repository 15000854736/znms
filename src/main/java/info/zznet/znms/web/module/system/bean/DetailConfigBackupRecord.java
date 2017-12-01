package info.zznet.znms.web.module.system.bean;

import java.util.Date;

public class DetailConfigBackupRecord {
	String id;
	String hostUuid;
	String hostName;
	String hostIp;
	String backupPath;
	String fileName;
	Date backupTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHostUuid() {
		return hostUuid;
	}
	public void setHostUuid(String hostUuid) {
		this.hostUuid = hostUuid;
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
	public String getBackupPath() {
		return backupPath;
	}
	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getBackupTime() {
		return backupTime;
	}
	public void setBackupTime(Date backupTime) {
		this.backupTime = backupTime;
	}
	
	
	
}
