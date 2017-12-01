package info.zznet.znms.web.module.common.service.impl;

import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.module.common.service.BaseService;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.module.common.page.Order;
import info.zznet.znms.web.module.common.page.PageBounds;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.BaseService;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 基层service
 * @author yuanjingtao
 *
 * @param <T1>分页实体
 * @param <T2>查询实体
 */
@Service("baseService")
public class BaseServiceImpl<T1,T2> implements BaseService<T1,T2> {
	/**
	 * 传入page对象以及order对象，返回pageBounds对象
	 */
	public PageBounds getPageBounds(Pager<T1> pager,List<Order> orders) {
        PageBounds bounds = new PageBounds((pager.getCurrent()+pager.getOffset())/pager.getLimit()+1, pager.getLimit(), orders);
        return bounds;
	}
	/**
	 * 通过page对象以及order对象查询分页数据
	 */
	public Pager<T1> findPagedObjects(Pager<T1> pager,Order order){
		if(order!=null){
			ArrayList<Order> orders = new ArrayList<Order>();
			orders.add(order);
			return findPagedObjects(pager,orders);
		}else{
			return findPagedObjects(pager,new ArrayList<Order>());
		}
		
	}
	/**
	 * 通过page对象以及order对象查询分页数据
	 */
	public Pager<T1> findPagedObjects(Pager<T1> pager){
			return findPagedObjects(pager,new ArrayList<Order>());
	}
	
	/**
	 * 通过page对象以及order对象查询分页数据
	 */
	@SuppressWarnings("unchecked")
	public Pager<T1> findPagedObjects(Pager<T1> pager,List<Order> orders) {
		//获取查询条件
		String search = pager.getSearch();
		T2 queryBean = null;
		try {
			queryBean = (T2) getQueryClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//根据search获取queryBean查询实体
		ObjectMapper objectMapper = new ObjectMapper(); 
		if(!StringUtil.isNullString(search)){
			try {
		        List<LinkedHashMap<String, Object>> list = objectMapper.readValue(search, List.class);
		        for (int i = 0; i < list.size(); i++) {
		            Map<String, Object> map = list.get(i);
		            String key = (String)map.get("key");
		            Object value = map.get("value");
		            if(!StringUtil.isNullString(value.toString())){
		            	try {
							Field field = getQueryClass().getDeclaredField(key);
							boolean accessible = field.isAccessible();
						    field.setAccessible(true);
						    if(field.getType()==Boolean.class){
						    	if(value.equals("0")){
						    		field.set(queryBean, false);
						    	}else{
						    		field.set(queryBean, true);
						    	}
						    }else if(field.getType()==Integer.class){
						    	field.set(queryBean, Integer.valueOf(value.toString()));
						    }else{
						    	field.set(queryBean, value);
						    }
							
							field.setAccessible(accessible);
						} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		        }
		    } catch (JsonParseException e) {
		        e.printStackTrace();
		    } catch (JsonMappingException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		//组装order的list
		if(orders!=null&&!StringUtil.isNullString(pager.getSort())&&!StringUtil.isNullString(pager.getOrder())){
			orders.add(Order.create(StringUtil.transToUnderLineStyle(pager.getSort()), pager.getOrder()));
		}
		PageBounds bounds = null;
		bounds = getPageBounds(pager,orders);
		long d1 = new Date().getTime();
		//查询分页数据
		List<T1> list = findByPage(bounds,queryBean);
		long d2 = new Date().getTime();
		System.out.println(d2-d1);
		//查询总数据并重新组装pager对象
		pager.setTotal((long)count(queryBean));
		pager.setRows(list);
		pager.setTotalpage((int)(pager.getTotal()/pager.getLimit())+1);
		return pager;
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public List<T1> findByPage(PageBounds bounds, T2 t) {
		return null;
	}
	//分页查询时获取查询实体的类类型
	@Override
	public Class<?> getQueryClass() {
		// TODO Auto-generated method stub
		return null;
	}
	//分页查询的count计数
	@Override
	public int count(T2 t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int findCountByPage(Pager pager) {
		String search = pager.getSearch();
		T2 queryBean = null;
		try {
			queryBean = (T2) getQueryClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//根据search获取siSchoolInfoBean
		ObjectMapper objectMapper = new ObjectMapper(); 
		if(!StringUtil.isNullString(search)){
			try {
		        List<LinkedHashMap<String, Object>> list = objectMapper.readValue(search, List.class);
		        for (int i = 0; i < list.size(); i++) {
		            Map<String, Object> map = list.get(i);
		            Set<String> set = map.keySet();
		            String key = (String)map.get("key");
		            Object value = map.get("value");
		            if(!StringUtil.isNullString(value.toString())){
		            	try {
							Field field = getQueryClass().getDeclaredField(key);
							boolean accessible = field.isAccessible();
						    field.setAccessible(true);
						    if(field.getType()==Boolean.class){
						    	if(value.equals("0")){
						    		field.set(queryBean, false);
						    	}else{
						    		field.set(queryBean, true);
						    	}
						    }else if(field.getType()==Integer.class){
						    	field.set(queryBean, Integer.valueOf(value.toString()));
						    }else if(field.getType()==Timestamp.class){
						    	field.set(queryBean, Timestamp.valueOf(value.toString()));
						    }else{
						    	field.set(queryBean, value);
						    }
							
							field.setAccessible(accessible);
						} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		        }
		    } catch (JsonParseException e) {
		        e.printStackTrace();
		    } catch (JsonMappingException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		return (int)count(queryBean);
	}


}
