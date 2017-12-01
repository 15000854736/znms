/**
 * 
 */
package info.zznet.znms.web.module.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import info.zznet.znms.base.dao.GraphTreeItemMapper;
import info.zznet.znms.base.entity.GraphTreeItem;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.system.service.GraphTreeItemService;

/**
 * @author dell001
 *
 */
@Service("graphTreeItemService")
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
public class GraphTreeItemServiceImpl extends BaseServiceImpl implements GraphTreeItemService{

	@Autowired
	private GraphTreeItemMapper graphTreeItemMapper;

	@Override
	public void addGraphTreeItem(GraphTreeItem graphTreeItem) {
		graphTreeItem.setGraphTreeItemUuid(UUIDGenerator.getGUID());
		//设置排序
		Integer sort = graphTreeItemMapper.findMaxSortItemByParentUuid(graphTreeItem.getParentUuid());
		if(null==sort){
			graphTreeItem.setSort(1);
		}else{
			graphTreeItem.setSort(sort+1);
		}
		graphTreeItemMapper.insert(graphTreeItem);
	}

	@Override
	public void updateGraphTreeItem(String title, String graphTreeItemUuid) {
		GraphTreeItem item = new GraphTreeItem();
		item.setTitle(title);
		item.setGraphTreeItemUuid(graphTreeItemUuid);
		graphTreeItemMapper.updateGraphTreeItem(item);
	}

	@Override
	public GraphTreeItem findItemByTitleAndParentUuid(String title,
			String parentUuid, String graphTreeItemUuid) {
		return graphTreeItemMapper.findItemByTitleAndParentUuid(title, parentUuid, graphTreeItemUuid);
	}

	@Override
	public void deleteGraphTreeItem(String graphTreeItemUuid) {
		//删除节点本身
		GraphTreeItem item = graphTreeItemMapper.selectByPrimaryKey(graphTreeItemUuid);
		List<GraphTreeItem> list = item.getChildList();
		graphTreeItemMapper.deleteByPrimaryKey(graphTreeItemUuid);
		//删除节点子节点
		if(null!=list && list.size()>0){
			for (GraphTreeItem graphTreeItem : list) {
				graphTreeItemMapper.deleteByPrimaryKey(graphTreeItem.getGraphTreeItemUuid());
			}
		}
		//节点重新排序
		graphTreeItemMapper.reSort(item.getSort(),item.getParentUuid());
	}

	@Override
	public void shiftGraphTreeItem(boolean direction, String graphTreeItemUuid) {
		GraphTreeItem item = graphTreeItemMapper.selectByPrimaryKey(graphTreeItemUuid);
		String parentUuid = item.getParentUuid();
		Integer sort = item.getSort();
		GraphTreeItem gti =null;
		if(direction){
			if(item.getSort().intValue()==1){
				return;
			}
			//上移
			gti = graphTreeItemMapper.findByParentUuidAndSort(parentUuid, sort-1);
			graphTreeItemMapper.upperShift(item);
			graphTreeItemMapper.downShift(gti);
		}else{
			if(graphTreeItemMapper.findByParentUuidAndSort(parentUuid, sort + 1) == null){
				return;
			}
			//下移
			gti = graphTreeItemMapper.findByParentUuidAndSort(parentUuid, sort+1);
			graphTreeItemMapper.downShift(item);
			graphTreeItemMapper.upperShift(gti);
		}
		
	}

	@Override
	public List<GraphTreeItem> findByHostId(String id) {
		return graphTreeItemMapper.findByHostId(id);
	}
	
}
