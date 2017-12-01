package info.zznet.znms.web.module.common.service;

import info.zznet.znms.web.module.common.page.Order;
import info.zznet.znms.web.module.common.page.PageBounds;
import info.zznet.znms.web.module.common.page.Pager;

import java.util.List;

/**
 * 基础service类
 * @author yuanjingtao
 *
 * @param <T1>分页实体
 * @param <T2>查询实体
 */
public interface BaseService<T1,T2>{
	public PageBounds getPageBounds(Pager<T1> pager,List<Order> orders);
	public Pager<T1> findPagedObjects(Pager<T1> pager);
	public Pager<T1> findPagedObjects(Pager<T1> pager,Order order);
	public Pager<T1> findPagedObjects(Pager<T1> pager,List<Order> orders);
	public Class<?> getQueryClass();
	public List<T1> findByPage(PageBounds bounds,T2 t);
	public int count(T2 t);
	public int findCountByPage(Pager pager);
}
