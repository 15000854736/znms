package info.zznet.znms.base.entity;

import java.util.Date;

/**
 * 配置备份设备实体类
 * 
 * @author dell001
 *
 */
public class BackupConfigurationDevice {
	// 主键
	private String deviceUuid;

	// 主机UUID
	private String hostUuid;

	// 启用状态 0：未启用; 1:启用
	// private Integer useStatus;

	private boolean useStatus;

	// 账户密码uuid
	private String accountPasswordUuid;

	// 备份周期 1:每天; 2:每周; 3:每月
	private Integer backupCycle;

	// 目录
	private String content;

	// 描述
	private String description;

	// 创建时间
	private Date createTime;

	// 主机
	private Host host;

	public String getDeviceUuid() {
		return deviceUuid;
	}

	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
	}

	public String getHostUuid() {
		return hostUuid;
	}

	public void setHostUuid(String hostUuid) {
		this.hostUuid = hostUuid;
	}

	/*
	 * public Integer getUseStatus() { return useStatus; }
	 * 
	 * public void setUseStatus(Integer useStatus) { this.useStatus = useStatus;
	 * }
	 */
	

	
	public String getAccountPasswordUuid() {
		return accountPasswordUuid;
	}

	public boolean isUseStatus() {
		return useStatus;
	}

	public void setUseStatus(boolean useStatus) {
		this.useStatus = useStatus;
	}

	public void setAccountPasswordUuid(String accountPasswordUuid) {
		this.accountPasswordUuid = accountPasswordUuid;
	}

	public Integer getBackupCycle() {
		return backupCycle;
	}

	public void setBackupCycle(Integer backupCycle) {
		this.backupCycle = backupCycle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

}