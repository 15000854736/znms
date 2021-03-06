/**
 * 
 */
package info.zznet.znms.web.module.system.service.impl;

import info.zznet.znms.base.dao.ExportLinkMapper;
import info.zznet.znms.base.dao.GraphMapper;
import info.zznet.znms.base.dao.GraphOidMapper;
import info.zznet.znms.base.dao.GraphTemplateMapper;
import info.zznet.znms.base.dao.HostMapper;
import info.zznet.znms.base.dao.ThresholdValueMapper;
import info.zznet.znms.base.entity.Graph;
import info.zznet.znms.base.entity.GraphOid;
import info.zznet.znms.base.entity.GraphTemplate;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.rrd.core.RrdTool;
import info.zznet.znms.base.rrd.exception.RrdExistsException;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.system.service.GraphService;

import java.util.Date;
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
@Service("graphService")
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
public class GraphServiceImpl extends BaseServiceImpl implements GraphService{
	
	@Autowired
	private GraphMapper graphMapper;
	
	@Autowired
	private GraphTemplateMapper graphTemplateMapper;
	
	@Autowired
	private HostMapper hostMapper;
	
	@Autowired
	private GraphOidMapper graphOidMapper;
	
	@Autowired
	private ExportLinkMapper exportLinkMapper;
	
	@Autowired
	private ThresholdValueMapper thresholdValueMapper;
	
	@Override
	public Pager<Graph> findPageList(Pager<Graph> pager) {
		JSONArray searchCondition = null;
		if (pager != null && !StringUtil.isNullString(pager.getSearch())) {
			searchCondition = JSONArray.fromObject(pager.getSearch());
		}
		List<Graph> list = graphMapper.findPageList(pager,
				searchCondition);
		pager.setRows(list);
		pager.setTotal(graphMapper.getCount(searchCondition));
		return pager;
	}

	@Override
	public void addGraph(Graph graph) {
		Host host = hostMapper.selectByPrimaryKey(graph.getHostUuid());
		graph.setHostIp(host.getHostIp());
		if(graph.getGraphType().intValue()==1){
			//添加基本图形
			graph.setGraphUuid(UUIDGenerator.getGUID());
			graph.setCreateTime(new Date());
			GraphTemplate template = graphTemplateMapper.selectByPrimaryKey(graph.getGraphTemplateId());
			graph.setGraphTemplateName(template.getGraphTemplateName());
			graphMapper.insert(graph);
			//添加图形和OID对应关系
			GraphOid graphOid = new GraphOid();
			graphOid.setGraphUuid(graph.getGraphUuid());
			graphOid.setGraphOidUuid(UUIDGenerator.getGUID());
			graphOid.setHostUuid(graph.getHostUuid());
			graphOid.setHostIp(graph.getHostIp());
			graphOid.setGraphTemplateId(graph.getGraphTemplateId());
			graphOid.setOid(template.getOid());
			try {
				String rrdFileName = RrdTool.registerRrd(template.getGraphTemplateSimpleName(), graph.getGraphUuid());
				graphOid.setRrdFileName(rrdFileName);
			} catch (RrdExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			graphOidMapper.insert(graphOid);
		}else{
			//添加图形和OID对应关系
			String[] indexArray = graph.getIndexs().split(",");
			String graphNameTemplate = graph.getGraphName();
			for(int i=0;i<indexArray.length;i++){
				//添加基本图形
				graph.setGraphUuid(UUIDGenerator.getGUID());
				graph.setCreateTime(new Date());
				//重新设置图形名称({主机名}-流量-{index})
				graph.setGraphName(graphNameTemplate.substring(0, graphNameTemplate.length()-4)+indexArray[i]);
				graphMapper.insert(graph);
				GraphOid graphOid = null;
				for(int a=0;a<=1;a++){
					graphOid = new GraphOid();
					graphOid.setGraphUuid(graph.getGraphUuid());
					graphOid.setGraphOidUuid(UUIDGenerator.getGUID());
					graphOid.setHostUuid(graph.getHostUuid());
					graphOid.setHostIp(host.getHostIp());
					String rrdFileName = "";
					if(a==0){
						graphOid.setOid("1.3.6.1.2.1.31.1.1.1.6."+indexArray[i]);
						graphOid.setFlowDirection("in");
						try {
							rrdFileName = RrdTool.registerRrd("netStream", graph.getGraphUuid());
						} catch (RrdExistsException e) {
							e.printStackTrace();
						}
					}else{
						graphOid.setOid("1.3.6.1.2.1.31.1.1.1.10."+indexArray[i]);
						graphOid.setFlowDirection("out");
					}
					graphOid.setRrdFileName(rrdFileName);
					graphOidMapper.insert(graphOid);
				}
				
			}
		}
		
	}

	@Override
	public void deleteByPrimaryKey(String graphUuid) {
		graphMapper.deleteByPrimaryKey(graphUuid);
		graphOidMapper.deleteByGraphUuid(graphUuid);
		//删除使用该图形的出口链路配置
		exportLinkMapper.deleteByGraphUuid(graphUuid);
		//删除引用该图形的阀值
		thresholdValueMapper.deleteByGraphUuid(graphUuid);
		
		RrdTool.deleteRrd(graphUuid);
	}

	@Override
	public void deleteGraphList(List<String> uuids) {
		for (String graphUuid : uuids) {
			deleteByPrimaryKey(graphUuid);
		}
	}

	@Override
	public boolean findGraphByCondition(Graph graph) {
		String hostUuid = graph.getHostUuid();
		int graphType = graph.getGraphType().intValue();
		if(graphType==1){
			//基本图形
			String graphTemplateId = graph.getGraphTemplateId();
			return graphMapper.findGraphByBasci(hostUuid, graphType, graphTemplateId)==null ?true:false;
		}else{
			//接口图形
			boolean boo = true;
			String[] indexArray = graph.getIndexs().split(",");
			for (String index : indexArray) {
				String oid = "1.3.6.1.2.1.31.1.1.1.6."+index;
				GraphOid graphOid = graphOidMapper.findGraphOidByInterface(hostUuid, oid);
				if(null != graphOid){
					boo = false;
					break;
				}
			}
			return boo;
		}
	}

	@Override
	public List<Graph> findAll() {
		return graphMapper.findAll();
	}

	@Override
	public List<Graph> findGraphByHost(String hostUuid) {
		return graphMapper.findGraphByHost(hostUuid);
	}

	@Override
	public int checkGraphType(String graphUuid) {
		return graphMapper.checkGraphType(graphUuid);
	}

}
