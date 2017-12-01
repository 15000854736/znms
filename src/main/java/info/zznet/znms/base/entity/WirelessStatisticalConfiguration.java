package info.zznet.znms.base.entity;

import java.util.Date;

/**
 * 无线统计配置实体
 * @author dell001
 *
 */
public class WirelessStatisticalConfiguration {
	
    private String wirelessStatisticalConfigurationUuid;

    private String hostUuid;

    //在线ap图形模板UUID
    private String apTemplateUuid;

    //在线用户图形模板uuid
    private String userTemplateUuid;

    private Date createTime;
    
    private Host host;
    
    private GraphTemplate apTemplate;
    
    private GraphTemplate userTemplate;

    public String getWirelessStatisticalConfigurationUuid() {
        return wirelessStatisticalConfigurationUuid;
    }

    public void setWirelessStatisticalConfigurationUuid(String wirelessStatisticalConfigurationUuid) {
        this.wirelessStatisticalConfigurationUuid = wirelessStatisticalConfigurationUuid;
    }

    public String getHostUuid() {
        return hostUuid;
    }

    public void setHostUuid(String hostUuid) {
        this.hostUuid = hostUuid;
    }

    public String getApTemplateUuid() {
		return apTemplateUuid;
	}

	public void setApTemplateUuid(String apTemplateUuid) {
		this.apTemplateUuid = apTemplateUuid;
	}

	public String getUserTemplateUuid() {
		return userTemplateUuid;
	}

	public void setUserTemplateUuid(String userTemplateUuid) {
		this.userTemplateUuid = userTemplateUuid;
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

	public GraphTemplate getApTemplate() {
		return apTemplate;
	}

	public void setApTemplate(GraphTemplate apTemplate) {
		this.apTemplate = apTemplate;
	}

	public GraphTemplate getUserTemplate() {
		return userTemplate;
	}

	public void setUserTemplate(GraphTemplate userTemplate) {
		this.userTemplate = userTemplate;
	}
    
}