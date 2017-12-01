package info.zznet.znms.base.bean;

public class ZlogSystemStatus {
	private int diskTotal;
	private int diskFree;
	private int diskAvail;
	private int diskUsed;
	private double cpuCerc;
	private double memTotal;
	private double memUsed;
	private double memFree;
	public double getCpuCerc() {
		return cpuCerc;
	}
	public void setCpuCerc(double cpuCerc) {
		this.cpuCerc = cpuCerc;
	}
	public int getDiskTotal() {
		return diskTotal;
	}
	public void setDiskTotal(int diskTotal) {
		this.diskTotal = diskTotal;
	}
	public int getDiskFree() {
		return diskFree;
	}
	public void setDiskFree(int diskFree) {
		this.diskFree = diskFree;
	}
	public int getDiskAvail() {
		return diskAvail;
	}
	public void setDiskAvail(int diskAvail) {
		this.diskAvail = diskAvail;
	}
	public int getDiskUsed() {
		return diskUsed;
	}
	public void setDiskUsed(int diskUsed) {
		this.diskUsed = diskUsed;
	}
	
	public double getMemTotal() {
		return memTotal;
	}
	public void setMemTotal(double memTotal) {
		this.memTotal = memTotal;
	}
	public double getMemUsed() {
		return memUsed;
	}
	public void setMemUsed(double memUsed) {
		this.memUsed = memUsed;
	}
	public double getMemFree() {
		return memFree;
	}
	public void setMemFree(double memFree) {
		this.memFree = memFree;
	}
}
