package info.zznet.znms.base.dao;

import info.zznet.znms.base.entity.GraphTreeItem;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("graphTreeItemMapper")
public interface GraphTreeItemMapper extends BaseMapper{
    int deleteByPrimaryKey(String graphTreeItemUuid);

    int insert(GraphTreeItem record);

    int insertSelective(GraphTreeItem record);

    GraphTreeItem selectByPrimaryKey(String graphTreeItemUuid);

    int updateByPrimaryKeySelective(GraphTreeItem record);

    int updateByPrimaryKey(GraphTreeItem record);

	/**
	 * 查找图形树的所有根节点或根主机
	 * @param graphTreeUuid
	 * @return
	 */
	List<GraphTreeItem> findAllRootByGraphTreeUuid(@Param("graphTreeUuid")String graphTreeUuid);

	/**
	 * @param graphTreeUuid
	 * @return
	 */
	List<GraphTreeItem> getNode(@Param("graphTreeUuid")String graphTreeUuid);

	/**
	 * @param nodeUuid
	 * @return
	 */
	List<GraphTreeItem> getHost(@Param("nodeUuid")String nodeUuid);

	/**
	 * @param parentUuid
	 * @return
	 */
	Integer findMaxSortItemByParentUuid(@Param("parentUuid")String parentUuid);

	/**
	 * @param title
	 * @param parentUuid
	 * @param graphTreeItemUuid
	 * @return
	 */
	GraphTreeItem findItemByTitleAndParentUuid(@Param("title")String title, @Param("parentUuid")String parentUuid,
			@Param("graphTreeItemUuid")String graphTreeItemUuid);

	/**
	 * @param graphTreeItem
	 */
	void updateGraphTreeItem(GraphTreeItem graphTreeItem);

	/**
	 * 删除节点重新排序
	 * @param sort
	 * @param parentUuid
	 */
	void reSort(@Param("sort")Integer sort, @Param("parentUuid")String parentUuid);

	/**
	 * @param list
	 */
	void deleteItem(@Param("list")List<String> list);

	/**
	 * @param sort
	 * @param parentUuid
	 */
	void upperShift(GraphTreeItem graphTreeItem);

	/**
	 * @param sort
	 * @param parentUuid
	 */
	void downShift(GraphTreeItem graphTreeItem);

	/**
	 * @param parentUuid
	 * @param sort
	 * @return
	 */
	GraphTreeItem findByParentUuidAndSort(@Param("parentUuid")String parentUuid, @Param("sort")int sort);

	/**
	 * @param hostId
	 * @return
	 */
	List<GraphTreeItem> findByHostId(@Param("hostId")String hostId);

	/**
	 * @param parentUuid
	 * @return
	 */
	List<String> findAllRootHostUuid(@Param("parentUuid")String parentUuid);

	/**
	 * 查找图形书下的所有一级节点(不包含主机)
	 * @param graphTreeUuid
	 * @return
	 */
	List<GraphTreeItem> findAllRootNode(@Param("parentUuid")String parentUuid);


}