package info.zznet.znms.base.entity;

import java.util.Date;

/**
 * 图形树实体类
 * @author dell001
 *
 */
public class Graph {
	
	public static final Integer BASIC_GRAPH_TYPE = 1;
	
	//图形uuid
    private String graphUuid;

    //主机uuid
    private String hostUuid;

    //主机ip
    private String hostIp;

    //图形类型   1:基本图形；2：接口图形
    private Integer graphType;

    //图形模板id
    private String graphTemplateId;

    //图形模板名
    private String graphTemplateName;

    //图形名
    private String graphName;
    
    //创建时间
    private Date CreateTime;
    
    private Host host;
    
    //接收接口图形 设备接口索引用
    private String indexs;

    public String getGraphUuid() {
        return graphUuid;
    }

    public void setGraphUuid(String graphUuid) {
        this.graphUuid = graphUuid;
    }

    public String getHostUuid() {
        return hostUuid;
    }

    public void setHostUuid(String hostUuid) {
        this.hostUuid = hostUuid;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public Integer getGraphType() {
        return graphType;
    }

    public void setGraphType(Integer graphType) {
        this.graphType = graphType;
    }

    public String getGraphTemplateId() {
		return graphTemplateId;
	}

	public void setGraphTemplateId(String graphTemplateId) {
		this.graphTemplateId = graphTemplateId;
	}

	public String getGraphTemplateName() {
        return graphTemplateName;
    }

    public void setGraphTemplateName(String graphTemplateName) {
        this.graphTemplateName = graphTemplateName;
    }

    public String getGraphName() {
        return graphName;
    }

    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public String getIndexs() {
		return indexs;
	}

	public void setIndexs(String indexs) {
		this.indexs = indexs;
	}
	
}