package info.zznet.znms.base.bean;

import java.io.Serializable;

public class SearchListBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String value;
	private String text;
	private boolean filterable;
	private String filterPlaceHolde;
	public SearchListBean() {
	}
	public SearchListBean(String value, String text) {
		this.value = value;
		this.text = text;
		this.filterable =false;
	}
	public SearchListBean(String value, String text,boolean filterable) {
		this.value = value;
		this.text = text;
		this.filterable =filterable;
		this.filterPlaceHolde="请输入关键字";
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isFilterable() {
		return filterable;
	}
	public void setFilterable(boolean filterable) {
		this.filterable = filterable;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getFilterPlaceHolde() {
		return filterPlaceHolde;
	}
	public void setFilterPlaceHolde(String filterPlaceHolde) {
		this.filterPlaceHolde = filterPlaceHolde;
	}
	
	
	
	
}
