/**
 * 
 */
package info.zznet.znms.web.module.system.service.impl;

import info.zznet.znms.base.dao.GraphTreeItemMapper;
import info.zznet.znms.base.dao.GraphTreeMapper;
import info.zznet.znms.base.dao.HostMapper;
import info.zznet.znms.base.entity.GraphTree;
import info.zznet.znms.base.entity.GraphTreeItem;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.system.service.GraphTreeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dell001
 *
 */
@Service("graphTreeService")
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
public class GraphTreeServiceImpl extends BaseServiceImpl implements GraphTreeService{

	@Autowired
	private GraphTreeMapper graphTreeMapper;
	
	@Autowired
	private GraphTreeItemMapper graphTreeItemMapper;
	
	@Autowired
	private HostMapper hostMapper;

	@Override
	public Pager<GraphTree> findPageList(Pager<GraphTree> pager) {
		JSONArray searchCondition = null;
		if (pager != null && !StringUtil.isNullString(pager.getSearch())) {
			searchCondition = JSONArray.fromObject(pager.getSearch());
		}
		List<GraphTree> list = graphTreeMapper.findPageList(pager,
				searchCondition);
		pager.setRows(list);
		pager.setTotal(graphTreeMapper.getCount(searchCondition));
		return pager;
	}

	@Override
	public void addGraphTree(GraphTree graphTree) {
		graphTree.setGraphTreeUuid(UUIDGenerator.getGUID());
		graphTree.setCreateTime(new Date());
		graphTreeMapper.insert(graphTree);
	}

	@Override
	public GraphTree findByPrimaryKey(String graphTreeUuid) {
		return graphTreeMapper.selectByPrimaryKey(graphTreeUuid);
	}

	@Override
	public void editGraphTree(GraphTree graphTree) {
		graphTreeMapper.updateByPrimaryKey(graphTree);
	}

	@Override
	public void deleteGraphTree(String id) {
		List<GraphTreeItem> list = graphTreeItemMapper.findAllRootByGraphTreeUuid(id);
		List<String> uuidList = new ArrayList<String>();
		if(null!=list && list.size()>0){
			for (GraphTreeItem item : list) {
				uuidList.add(item.getGraphTreeItemUuid());
				List<GraphTreeItem> childList = item.getChildList();
				if(childList!=null && childList.size()>0){
					for (GraphTreeItem graphTreeItem : childList) {
						uuidList.add(graphTreeItem.getGraphTreeItemUuid());
					}
				}
			}
			graphTreeItemMapper.deleteItem(uuidList);
		}
		graphTreeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByUuidList(List<String> uuids) {
		for (String uuid : uuids) {
			deleteGraphTree(uuid);
		}
	}

	@Override
	public GraphTree checkNameRepeat(String graphTreeName, String graphTreeUuid) {
		return graphTreeMapper.checkNameRepeat(graphTreeName, graphTreeUuid);
	}

	@Override
	public List<GraphTree> findAll() {
		return graphTreeMapper.findAll();
	}

	@Override
	public List<Host> findAllHostNotRepeat(String graphTreeUuid) {
		HashSet<String> set = new HashSet<String>();
		//查找一级目录下的所有主机
		List<String> hostUuidList = graphTreeItemMapper.findAllRootHostUuid(graphTreeUuid);
		set.addAll(hostUuidList);
		//查找一级目录下的所有节点（不包含主机）
		List<GraphTreeItem> itemList = graphTreeItemMapper.findAllRootNode(graphTreeUuid);
		for (GraphTreeItem graphTreeItem : itemList) {
			List<String> uuidList = graphTreeItemMapper.findAllRootHostUuid(graphTreeItem.getGraphTreeItemUuid());
			set.addAll(uuidList);
		}
		List<String> list = new ArrayList<String>();
		list.addAll(set);
		List<Host> hostList = new ArrayList<Host>();
		for (String hostUuid : list) {
			hostList.add(hostMapper.selectByPrimaryKey(hostUuid));
		}
		return hostList;
	}
}
