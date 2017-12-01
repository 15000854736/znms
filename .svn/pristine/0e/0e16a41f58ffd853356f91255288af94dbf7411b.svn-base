package info.zznet.znms.base.dao;

import info.zznet.znms.base.entity.GraphTree;
import info.zznet.znms.web.module.monitor.bean.GraphItem;
import info.zznet.znms.web.module.monitor.bean.GraphTreeItemBean;
import info.zznet.znms.web.module.monitor.bean.MonitorQueryBean;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("graphExtendMapper")
public interface GraphExtendMapper extends BaseMapper{
    
	List<GraphItem> findPagedData(@Param("condition")MonitorQueryBean condition);

	List<GraphTree> findAllTrees();
	
	List<GraphTreeItemBean> findByParentId(@Param("id")String id);
}