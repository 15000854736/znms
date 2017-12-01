/**
 * 
 */
package info.zznet.znms.web.module.system.service;

import java.util.List;

import info.zznet.znms.base.entity.Graph;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.BaseService;

/**
 * @author dell001
 *
 */
public interface GraphService extends BaseService{

	/**
	 * @param pager
	 * @return
	 */
	Pager<Graph> findPageList(Pager<Graph> pager);

	/**
	 * @param graph
	 */
	void addGraph(Graph graph);

	/**
	 * @param graphUuid
	 */
	void deleteByPrimaryKey(String graphUuid);

	/**
	 * @param uuids
	 */
	void deleteGraphList(List<String> uuids);

	/**
	 * 根据条件判断图形是否已存在
	 * @param graph
	 * @return
	 */
	boolean findGraphByCondition(Graph graph);

	/**
	 * 查找所有图形
	 * @return
	 */
	List<Graph> findAll();

	/**
	 * 
	 * @param hostUuid
	 * @return
	 */
	List<Graph> findGraphByHost(String hostUuid);

	/**
	 * 查询图形类型
	 * @param graphUuid
	 * @return
	 */
	int checkGraphType(String graphUuid);

}
