/**
 * 
 */
package info.zznet.znms.web.module.system.service.impl;

import info.zznet.znms.base.dao.ApRegionMapper;
import info.zznet.znms.base.dao.BackupConfigurationDeviceMapper;
import info.zznet.znms.base.dao.ExportLinkMapper;
import info.zznet.znms.base.dao.GraphMapper;
import info.zznet.znms.base.dao.GraphOidMapper;
import info.zznet.znms.base.dao.HostMapper;
import info.zznet.znms.base.dao.ThresholdValueMapper;
import info.zznet.znms.base.dao.WirelessStatisticalConfigurationMapper;
import info.zznet.znms.base.entity.ApInformation;
import info.zznet.znms.base.entity.ApRegion;
import info.zznet.znms.base.entity.Graph;
import info.zznet.znms.base.entity.GraphTemplate;
import info.zznet.znms.base.entity.GraphTreeItem;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.entity.dto.GraphDTO;
import info.zznet.znms.base.job.ApInfomationJob;
import info.zznet.znms.base.rrd.core.RrdFetcher;
import info.zznet.znms.base.rrd.exception.RrdQueryException;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.spider.constants.SnmpConstants;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.module.apInformation.bean.DeviceSearchBean;
import info.zznet.znms.web.module.apInformation.service.ApInformationService;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.system.service.GraphTreeItemService;
import info.zznet.znms.web.module.system.service.HostService;
import info.zznet.znms.web.util.ConfigUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.rrd4j.core.FetchData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dell001
 *
 */
@Service("hostService")
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
public class HostServiceImpl extends BaseServiceImpl implements HostService{
	
	@Autowired
	private HostMapper hostMapper;
	
	@Autowired
	private GraphTreeItemService graphTreeItemService;
	
	@Autowired
	private WirelessStatisticalConfigurationMapper wirelessMapper;
	
	@Autowired
	private GraphMapper graphMapper;
	
	@Autowired
	private GraphOidMapper graphOidMapper;
	
	@Autowired
	private ExportLinkMapper exportLinkMapper;
	
	@Autowired
	private BackupConfigurationDeviceMapper deviceMapper;
	
	@Autowired
	private ThresholdValueMapper thresholdValueMapper;

	@Autowired
	private ApInfomationJob apInfomationJob;
	
	@Autowired
	private ApRegionMapper apRegionMapper;
	
	@Autowired
	private ApInformationService apInformationService;
	
	@Override
	public Pager<Host> findPageList(Pager<Host> pager) {
		JSONArray searchCondition = null;
		if (pager != null && !StringUtil.isNullString(pager.getSearch())) {
			searchCondition = JSONArray.fromObject(pager.getSearch());
		}
		List<Host> list = hostMapper.findPageList(pager,
				searchCondition);
		pager.setRows(list);
		pager.setTotal(hostMapper.getCount(searchCondition));
		return pager;
	}

	@Override
	public void update(Host host) {
		hostMapper.updateByPrimaryKey(host);
		WebRuntimeData.instance.saveHost(host);
	}

	@Override
	public void addHost(Host host) {
		//新增主机
		String id= UUIDGenerator.getGUID();
		host.setId(id);
		//设置版本
		switch (host.getSnmpVersion()) {
		case 0:
			host.setSnmpVersion(Integer.valueOf(SnmpConstants.SNMP_VERSION_0));
			break;
		case 1:
			host.setSnmpVersion(Integer.valueOf(SnmpConstants.SNMP_VERSION_1));
			break;
		case 2:
			host.setSnmpVersion(Integer.valueOf(SnmpConstants.SNMP_VERSION_2));
			break;
		case 3:
			host.setSnmpVersion(Integer.valueOf(SnmpConstants.SNMP_VERSION_3));
			break;
		default:
			break;
		}
		if(host.getAvailabilityMethod()!=0){
			//设置可选性选项  默认检测超时时间和重试次数
			host.setPingTimeout(Integer.valueOf(ConfigUtil.getString("host.selectiveOption.checkTimeout")));
			host.setPingRetries(Integer.valueOf(ConfigUtil.getString("host.selectiveOption.pingRetries")));
			if(host.getAvailabilityMethod()==2){
				//当检测方法为ping时设置可选性选项 默认包类型(UDP类型)和检测端口
				host.setPingMethod(Short.valueOf(ConfigUtil.getString("host.selectiveOption.pingMethod")));
				host.setPingPort(Integer.valueOf(ConfigUtil.getString("host.selectiveOption.pingPort")));
			}
		}
		if(host.getSnmpVersion()!=0){
			//设置默认 snmp选项 默认端口和超时时间
			host.setSnmpPort(Integer.valueOf(ConfigUtil.getString("host.snmpOption.snmpPort")));
			host.setSnmpTimeout(Integer.valueOf(ConfigUtil.getString("host.snmpOption.snmpTimeout")));
		}
		//设置主机禁用日期和启用日期
		if(host.getStatus()>0){
			host.setStatusRecDate(new Date());
		}else{
			host.setStatusFailDate(new Date());
		}
		//工作状态设置为正常
		host.setHostWorkStatus(1);
		hostMapper.insert(host);
		WebRuntimeData.instance.saveHost(host);

		if(host.getType()==3){
			//无线控制器，更新
			apInfomationJob.scanAc(host,new ArrayList<String>());
		}


	}

	@Override
	public Host findHostById(String id) {
		return hostMapper.selectByPrimaryKey(id);
	}

	@Override
	public void deleteByPrimaryKey(String id) {
		hostMapper.deleteByPrimaryKey(id);
		//删除主机下的图形
		graphMapper.deleteGraphByHostUuid(id);
		graphOidMapper.deleteGraphByHostUuid(id);
		//删除主机的无线统计配置
		wirelessMapper.deleteByHostUuid(id);
		//删除主机下的出口链路配置
		exportLinkMapper.deleteByHostUuid(id);
		//删除配置备份下引用该主机的设备
		deviceMapper.deleteByHostUuid(id);
		//删除引用该主机的阀值
		thresholdValueMapper.deleteByHostUuid(id);
	}

	@Override
	public String deleteByUuidList(List<String> uuids) {
		for (String id : uuids) {
			//判断主机能否删除
	   		List<GraphTreeItem> itemList = graphTreeItemService.findByHostId(id);
	   		if(null!=itemList && itemList.size()>0){
	   			//判断主机是否在图形树里
	   			return "{\"result\":false,\"msg\":\"图形树中使用该主机，删除失败\"}";
	   		}
			hostMapper.deleteByPrimaryKey(id);
		}
		return JSONObject.fromObject(findPageList(new Pager())).toString();
	}

	@Override
	public void updateHostEnable(List<String> uuidList) {
		hostMapper.updateHostEnable(uuidList);
	}

	@Override
	public void updateHostDisable(List<String> uuidList) {
		hostMapper.updateHostDisable(uuidList);
	}

	@Override
	public List<Host> findApplicableHost(String nodeUuid) {
		return hostMapper.findApplicableHost(nodeUuid);
	}

	@Override
	public List<Host> findDeviceHost(String deviceUuid) {
		return hostMapper.findDeviceHost(deviceUuid);
	}

	@Override
	public List<Host> findAll() {
		return hostMapper.findAll();
	}

	@Override
	public Host checkHostIp(Host host) {
		return hostMapper.checkHostIp(host.getHostIp(), host.getId());
	}

	@Override
	public Host checkHostName(Host host) {
		return hostMapper.checkHostName(host.getHostName(), host.getId());
	}

	@Override
	public void updateHostWorkStatusByIP(String ip, boolean isReachable) {
		int hostWorkStatus = isReachable ?1:0;
		hostMapper.updateHostWorkStatusByIP(ip, hostWorkStatus);
	}

	@Override
	public List<Host> findAllAc() {
		return hostMapper.findAllAc();
	}

	@Override
	public int getDeviceCountByCondition(int[] deviceTypeArray,
			int hostWorkStatus) {
		return hostMapper.getDeviceCountByCondition(deviceTypeArray, hostWorkStatus);
	}

	@Override
	public List<Host> getMainDeviceMonitorData(int[] defaultAccessArray) {
		List<Host> list = hostMapper.getMainDeviceMonitorData(defaultAccessArray);
		//设置主机cpu使用和内存使用率
		for (Host host : list) {
			for(int i=0;i<2;i++){
				String graphTemplateId = (i==0 ?GraphTemplate.CPU_TYPE_ID:GraphTemplate.MEMORY_TYPE_ID);
				GraphDTO graphDTO = graphMapper.findGraphDTO(host.getId(), Graph.BASIC_GRAPH_TYPE, graphTemplateId);
				if(null != graphDTO){
					info.zznet.znms.base.rrd.bean.Graph graph = new info.zznet.znms.base.rrd.bean.Graph();
					try {
						graph = RrdFetcher.fetchDataAndConvertToGraph(graphDTO.getGraphTemplateSimpleName(), graphDTO.getGraphUuid());
					} catch (RrdQueryException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(null!=graph){
						if(i==0){
							host.setCpuUsePercent(graph.getData().get(0).getData().get(0));
						}else{
							host.setMemoryUsePercent(graph.getData().get(0).getData().get(0));
						}
					}
				}
			}
		}
		return list;
	}
	
	@Override
	public List<Host> getShutDownHost(int hostWorkStatus){
		return hostMapper.findShutDownHost(hostWorkStatus);
	}

	@Override
	public List findHostByCondition(DeviceSearchBean bean) {
		String hostIp = bean.getHostIp();
		String hostName = bean.getHostName();
		int hostType = bean.getHostType();
		String apRegionUuid = bean.getApRegionUuid();
		int apPositionStatus = bean.getApPositionStatus(); 
		return hostMapper.findHostByCondition(hostIp, hostName, hostType, apPositionStatus, apRegionUuid);
	}

	@Override
	public void updateHostAxis(String[] indexes, String hostAxis) {
		boolean needDisperse = indexes.length > 1;
		for(String index : indexes) {
			String[] indexArr = StringUtils.split(index,",");
			if(null!=index&&indexArr.length==2){
				String _apAxis = needDisperse?apInformationService.disperse(hostAxis):hostAxis;
				hostMapper.updateApAxis(indexArr[0], _apAxis);
			}
		}
	}
	
	@Override
	public void updateHostAxisByRegion(String[] indexes, String apRegionUuid) {
		ApRegion apRegion = apRegionMapper.selectByPrimaryKey(apRegionUuid);
		String[] regionArray = apRegion.getApRegionCoordinate().split(",");
		for(String index : indexes) {
			String[] indexArr = StringUtils.split(index,",");
			if(null!=index&&indexArr.length==2){
				String apAxis = apInformationService.generateRandomApaxis(regionArray);
				hostMapper.updateApAxisByRegion(indexArr[0], apAxis, apRegionUuid);
			}
		}
	}
	
}
