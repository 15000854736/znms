/**
 * 
 */
package info.zznet.znms.web.module.hostListbox.service.impl;

import info.zznet.znms.base.entity.GraphTree;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.hostListbox.bean.HostListboxBean;
import info.zznet.znms.web.module.hostListbox.service.HostListboxService;
import info.zznet.znms.web.module.system.service.GraphTreeService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dell001
 *
 */
@Service("hostListboxService")
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
public class HostListboxServiceImpl extends BaseServiceImpl implements HostListboxService{

	@Autowired
	private GraphTreeService graphTreeService;

	@Override
	public List<HostListboxBean> findHostListbox() {
		//查找所有图形树
		List<GraphTree> treeList = graphTreeService.findAll();
		List<HostListboxBean> beanList = new ArrayList<HostListboxBean>();
		//查找图形树下的所有主机，重复主机保留一个
		for (GraphTree graphTree : treeList) {
			List<Host> hostList = graphTreeService.findAllHostNotRepeat(graphTree.getGraphTreeUuid());
			HostListboxBean bean = new HostListboxBean();
			bean.setGraphTreeName(graphTree.getGraphTreeName());
			bean.setHostList(hostList);
			beanList.add(bean);
		}		
		return beanList;
	}
	
}
