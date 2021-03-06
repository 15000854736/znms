package info.zznet.znms.base.entity;

import java.util.Date;

public class ExportLink {
    private String exportLinkUuid;

    private String hostUuid;

    //图形
    private String graphUuid;

    //出口链路描述
    private String exportLinkDescription;

    //最大带宽
    private Long maxBandWidth;

    private Date createTime;
    
    private Host host;
    
    private Graph graph;
    
    private String maxBandWidthText;

    public String getExportLinkUuid() {
        return exportLinkUuid;
    }

    public void setExportLinkUuid(String exportLinkUuid) {
        this.exportLinkUuid = exportLinkUuid;
    }

    public String getHostUuid() {
        return hostUuid;
    }

    public void setHostUuid(String hostUuid) {
        this.hostUuid = hostUuid;
    }

    public String getGraphUuid() {
		return graphUuid;
	}

	public void setGraphUuid(String graphUuid) {
		this.graphUuid = graphUuid;
	}

	public String getExportLinkDescription() {
        return exportLinkDescription;
    }

    public void setExportLinkDescription(String exportLinkDescription) {
        this.exportLinkDescription = exportLinkDescription;
    }

    public Long getMaxBandWidth() {
        return maxBandWidth;
    }

    public void setMaxBandWidth(Long maxBandWidth) {
        this.maxBandWidth = maxBandWidth;
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

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public String getMaxBandWidthText() {
		return maxBandWidthText;
	}

	public void setMaxBandWidthText(String maxBandWidthText) {
		this.maxBandWidthText = maxBandWidthText;
	}
    
}