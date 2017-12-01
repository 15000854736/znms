package info.zznet.znms.web.module.monitor.bean;


public class MonitorQueryBean {
	
	private String fromTime;
	private String toTime;
	private String graphTemplateId;
	private String hostGroup;
	private String subNodeId;
	private String hostUuid;
	private String graphName;
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String getGraphTemplateId() {
		return graphTemplateId;
	}
	public void setGraphTemplateId(String graphTemplateId) {
		this.graphTemplateId = graphTemplateId;
	}
	public String getHostGroup() {
		return hostGroup;
	}
	public void setHostGroup(String hostGroup) {
		this.hostGroup = hostGroup;
	}
	public String getSubNodeId() {
		return subNodeId;
	}
	public void setSubNodeId(String subNodeId) {
		this.subNodeId = subNodeId;
	}
	public String getHostUuid() {
		return hostUuid;
	}
	public void setHostUuid(String hostUuid) {
		this.hostUuid = hostUuid;
	}
	public String getGraphName() {
		return graphName;
	}
	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}
	
	
}
