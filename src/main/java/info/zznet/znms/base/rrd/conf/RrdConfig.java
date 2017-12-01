package info.zznet.znms.base.rrd.conf;

import info.zznet.znms.base.constants.SystemConstants;


/**
 * rrd配置
 */
public class RrdConfig {
	
	private String dataId;
	private String dataName;
	private RrdTemplate template;
	
	public RrdConfig(){}
	
	public RrdConfig(String dataId, RrdTemplate template){
		this.dataId = dataId;
		this.template = template;
	}
	
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public RrdTemplate getTemplate() {
		return template;
	}
	public void setTemplate(RrdTemplate template) {
		this.template = template;
	}

	@Override
	public String toString() {
		return "RrdConfig [dataId=" + dataId + ", template=" + template + "]";
	}

}
