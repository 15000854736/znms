package info.zznet.znms.base.rrd.bean;

import java.util.List;

/**
 * 分项
 */
public class SubItem {
	private String name;
	private String type = "line";
	private List<String> data;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	
	
}
