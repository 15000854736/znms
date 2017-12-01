package info.zznet.znms.base.entity;

import java.util.Date;

public class SystemLogStatistics{
	
	//日志类型id
	private Integer facilityId;

	//主机id
    private String hostId;

    //生成时间
    private Date insertTime;

    //日志级别id
    private Integer priorityId;
	
    //记录数
    private Integer records;

	public Integer getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Integer facilityId) {
		this.facilityId = facilityId;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Integer getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(Integer priorityId) {
		this.priorityId = priorityId;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

}