package info.zznet.znms.web.module.common.page;

import java.io.Serializable;
import java.util.List;

public final class Pager<T> implements Serializable {

	private static final long serialVersionUID = -3386682283688735911L;

	private Long total;

	private List<T> rows;
	
	private int limit = 10;

	private int offset = 0;

	private String order = "asc";
	
	/** 当前页  **/
	private int current=1;
	/** 总页数  */
	private int totalpage;
	private String sort;
	/**
	 * 查询条件
	 */
	private String search;
	
	/**
	 * 扩展信息
	 */
	private String append;

	public Pager() {
	}

	public Pager(int limit, int offset, String order) {
		this.limit = limit;
		this.offset = offset;
		this.order = order;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
		if(total>0){
			if(total%limit==0){
				totalpage = total.intValue()/limit;
			}else{
				totalpage = total.intValue()/limit+1;
			}
		}
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getAppend() {
		return append;
	}

	public void setAppend(String append) {
		this.append = append;
	}
}
