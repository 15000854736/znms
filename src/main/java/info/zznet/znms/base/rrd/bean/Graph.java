package info.zznet.znms.base.rrd.bean;

import java.util.List;

/**
 * 图表
 */
public class Graph {
	
	private String templateName;
	
	private String title;
	
	private List<String> subitemLabels;
	
	private List<String> xAxis;
	
	private List<SubItem> data;
	
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getSubitemLabels() {
		return subitemLabels;
	}

	public void setSubitemLabels(List<String> subitemLabels) {
		this.subitemLabels = subitemLabels;
	}

	public List<String> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}

	public List<SubItem> getData() {
		return data;
	}

	public void setData(List<SubItem> data) {
		this.data = data;
	}
	
}
