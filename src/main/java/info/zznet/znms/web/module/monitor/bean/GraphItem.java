package info.zznet.znms.web.module.monitor.bean;

import info.zznet.znms.base.rrd.bean.Graph;

/**
 *	图像单元
 */
public class GraphItem {
	private String graphUuid;
	private Integer graphType;
	private String hostUuid;
	private String hostName;
	private String hostIp;
	private String graphName;
	private String graphSimpleName;
	private String templateId;
	private Graph graphData;
	public String getGraphUuid() {
		return graphUuid;
	}
	public void setGraphUuid(String graphUuid) {
		this.graphUuid = graphUuid;
	}
	public Integer getGraphType() {
		return graphType;
	}
	public void setGraphType(Integer graphType) {
		this.graphType = graphType;
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
	public String getGraphName() {
		return graphName;
	}
	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}
	public String getGraphSimpleName() {
		return graphSimpleName;
	}
	public void setGraphSimpleName(String graphSimpleName) {
		this.graphSimpleName = graphSimpleName;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public Graph getGraphData() {
		return graphData;
	}
	public void setGraphData(Graph graphData) {
		this.graphData = graphData;
	}
	
	
}
