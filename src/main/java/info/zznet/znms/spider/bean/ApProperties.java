package info.zznet.znms.spider.bean;

public class ApProperties {
	
	//AP的MAC地址
	private String apMac;
	
	//当前在线人数
	private String onLineNum;
	
	//最大在线人数
	private String onLineMaxNum;
	
	//AP的IP地址
	private String apIp;
	
	//AP的管理状态
	private String adminStatus;
	
	//AP的序列号
	private String apSn;

	//AP NAME
	private String apName;

	public String getApMac() {
		return apMac;
	}

	public void setApMac(String apMac) {
		this.apMac = apMac;
	}

	public String getOnLineNum() {
		return onLineNum;
	}

	public void setOnLineNum(String onLineNum) {
		this.onLineNum = onLineNum;
	}

	public String getOnLineMaxNum() {
		return onLineMaxNum;
	}

	public void setOnLineMaxNum(String onLineMaxNum) {
		this.onLineMaxNum = onLineMaxNum;
	}

	public String getApIp() {
		return apIp;
	}

	public void setApIp(String apIp) {
		this.apIp = apIp;
	}

	public String getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}

	public String getApSn() {
		return apSn;
	}

	public void setApSn(String apSn) {
		this.apSn = apSn;
	}

	public String getApName() {
		return apName;
	}

	public void setApName(String apName) {
		this.apName = apName;
	}
}
