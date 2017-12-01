package info.zznet.znms.base.bean;

import java.util.concurrent.ConcurrentLinkedQueue;

public class HostPingResult {
	private int success;
	private int fail;
	private ConcurrentLinkedQueue<Boolean> resultList;
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	public int getFail() {
		return fail;
	}
	public void setFail(int fail) {
		this.fail = fail;
	}
	public ConcurrentLinkedQueue<Boolean> getResultList() {
		return resultList;
	}
	public void setResultList(ConcurrentLinkedQueue<Boolean> resultList) {
		this.resultList = resultList;
	}
	
	
}
