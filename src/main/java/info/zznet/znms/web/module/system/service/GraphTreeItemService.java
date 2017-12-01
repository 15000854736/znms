/**
 * 
 */
package info.zznet.znms.web.module.system.service;

import info.zznet.znms.base.entity.GraphTreeItem;
import info.zznet.znms.web.module.common.service.BaseService;

import java.util.List;

/**
 * @author dell001
 *
 */
public interface GraphTreeItemService extends BaseService{

	/**
	 * @param graphTreeItem
	 */
	void addGraphTreeItem(GraphTreeItem graphTreeItem);

	/**
	 * @param title
	 * @param graphTreeItemUuid
	 */
	void updateGraphTreeItem(String title,String graphTreeItemUuid);

	/**
	 * 根据父uuid和节点uuid查找节点标题是否重复
	 * @param title
	 * @param parentUuid
	 * @param graphTreeItemUuid
	 * @return
	 */
	GraphTreeItem findItemByTitleAndParentUuid(String title,String parentUuid,
			String graphTreeItemUuid);

	/**
	 *删除节点及其子节点
	 * @param graphTreeItemUuid
	 */
	void deleteGraphTreeItem(String graphTreeItemUuid);

	/**
	 * @param direction
	 * @param graphTreeItemUuid
	 */
	void shiftGraphTreeItem(boolean direction, String graphTreeItemUuid);

	/**
	 * @param id
	 * @return
	 */
	List<GraphTreeItem> findByHostId(String id);

}
