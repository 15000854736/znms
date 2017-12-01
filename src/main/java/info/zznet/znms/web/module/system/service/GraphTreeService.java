/**
 * 
 */
package info.zznet.znms.web.module.system.service;

import java.util.List;

import info.zznet.znms.base.entity.GraphTree;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.BaseService;

/**
 * @author dell001
 *
 */
public interface GraphTreeService extends BaseService{

	/**
	 * @param pager
	 * @return
	 */
	Pager<GraphTree> findPageList(Pager<GraphTree> pager);

	/**
	 * @param graphTree
	 */
	void addGraphTree(GraphTree graphTree);

	/**
	 * @param graphTreeUuid
	 * @return
	 */
	GraphTree findByPrimaryKey(String graphTreeUuid);

	/**
	 * @param graphTree
	 */
	void editGraphTree(GraphTree graphTree);

	/**
	 * 删除单个图形树
	 * @param id
	 */
	void deleteGraphTree(String id);

	/**
	 * 删除多个图形树
	 * @param uuids
	 */
	void deleteByUuidList(List<String> uuids);

	/**
	 * @param graphTreeName
	 * @param graphTreeUuid
	 * @return
	 */
	GraphTree checkNameRepeat(String graphTreeName, String graphTreeUuid);

	/**
	 * @return
	 */
	List<GraphTree> findAll();

	/**
	 * 查找图形树下的所有主机（重复主机只取一个）
	 * @param graphTreeUuid
	 */
	List<Host> findAllHostNotRepeat(String graphTreeUuid);

}
