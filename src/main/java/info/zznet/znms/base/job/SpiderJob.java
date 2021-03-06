package info.zznet.znms.base.job;

import info.zznet.znms.base.dao.GraphOidMapper;
import info.zznet.znms.base.dao.GraphTemplateMapper;
import info.zznet.znms.base.entity.GraphOid;
import info.zznet.znms.base.entity.GraphTemplate;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.spider.Engine;
import info.zznet.znms.spider.bean.ScanHost;
import info.zznet.znms.spider.bean.ScanItem;
import info.zznet.znms.web.module.system.service.HostService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by shenqilei on 2016/9/21.
 */
@Service
@DisallowConcurrentExecution
public class SpiderJob {

    @Autowired
    private Engine engine;
    
    @Autowired
    private HostService hostService;
    
    @Autowired
    private GraphOidMapper graphOidMapper;
    
    @Autowired
    private GraphTemplateMapper graphTemplateMapper;

    /**
     * 同步主机相关信息
     */
    @Scheduled(fixedDelay = 60*1000)
    public void syncHostInfo(){
        Map<String,ScanHost> hosts = Engine.hosts;
        List<Host> hostList = hostService.findAll();
        List<String> ipList = new ArrayList<String>();
        for (Host host : hostList) {
        	ipList.add(host.getHostIp());
        	ScanHost scanHost = new ScanHost();
        	scanHost.setName(host.getHostName());
        	scanHost.setCommunity(host.getSnmpCommunity());
        	scanHost.setIp(host.getHostIp());
        	scanHost.setSnmpAuthProtocol(host.getSnmpAuthProtocol());
        	scanHost.setSnmpContext(host.getSnmpContext());
        	scanHost.setSnmpPassword(host.getSnmpPassword());
        	scanHost.setSnmpPort(String.valueOf(host.getSnmpPort()));
        	scanHost.setSnmpPrivPassphrase(host.getSnmpPrivPassphrase());
        	scanHost.setSnmpPrivProtocol(host.getSnmpPrivProtocol());
        	scanHost.setSnmpUserName(host.getSnmpUserName());
        	scanHost.setSnmpVersion(String.valueOf(host.getSnmpVersion()));
        	scanHost.setStatus(String.valueOf(host.getStatus()));
        	scanHost.setReachable(host.getHostWorkStatus()==1?true:false);
        	scanHost.setAvailabilityMethod(host.getAvailabilityMethod());
        	scanHost.setType(host.getType());
        	scanHost.setApRegionUuid(host.getApRegionUuid());
        	scanHost.setHostAxis(host.getHostAxis());
        	List<ScanItem> itemList = new ArrayList<ScanItem>();
        	//查找主机下所有graphoid记录
        	List<GraphOid> graphOidList = graphOidMapper.findByHostUuid(host.getId());
        	for (GraphOid graphOid : graphOidList) {
        		ScanItem scanItem = new ScanItem();
        		scanItem.setRrdFile(graphOid.getRrdFileName());
        		scanItem.setOid(graphOid.getOid());
        		scanItem.setRrdDataId(graphOid.getGraphUuid());
        		if(StringUtil.isNullString(graphOid.getGraphTemplateId())){
        			//接口图形
        			scanItem.setRrdTemplateName("netStream");
        			if(scanItem.getOid().startsWith("1.3.6.1.2.1.31.1.1.1.6")){
        				//下行流量  流入
        				scanItem.setRrdDS("downStream");
        			}else{
        				//上行流量  流除
        				scanItem.setRrdDS("upStream");
        			}
        		}else{
        			//基本图形
        			GraphTemplate template = graphTemplateMapper.selectByPrimaryKey(graphOid.getGraphTemplateId());
        			scanItem.setRrdTemplateName(template.getGraphTemplateSimpleName());
        			scanItem.setRrdDS(template.getGraphTemplateSimpleName());
        		}
        		itemList.add(scanItem);
			}
        	scanHost.setScanItems(itemList);
        	hosts.put(host.getHostIp(), scanHost);
		}
        //去掉不存在的host
        for(String hostIp :hosts.keySet()){
        	if(!ipList.contains(hostIp)){
        		hosts.remove(hostIp);
        	}
        }

    }



    //@Scheduled(fixedDelay = 60*1000)
    public void test(){
        /*ScanHost n18k = new ScanHost();
        n18k.setIp("10.40.255.253");
        n18k.setName("n18k");
        n18k.setSnmpVersion(SnmpConstants.SNMP_VERSION_2);
        n18k.setStatus(SnmpConstants.HOST_STATUS_UP);
*/
        /*List<Interface> result = SnmpUtil.getInterfaces(n18k);
        for (Interface intf:result){
            ZNMSLogger.info(intf.toString());
        }*/
        //ZNMSLogger.info("config test:"+ ConfigUtil.getString("config.test"));
    }

    /**
     * 检测各主机可用性
     */
    @Scheduled(fixedDelay = 20*1000)
    public void checkHostStatus(){
        engine.check();
    }

    /**
     * 检查队列大小
     */
    @Scheduled(fixedDelay =60*1000)
    public void checkQueueSize(){
        engine.checkQueueSize();
    }

    /**
     * 扫描主机，获取信息
     */
    @Scheduled(initialDelay = 60*1000,fixedDelay = 60*1000)
    public void scanHost(){
//        ZNMSLogger.info("开始采集信息");
        engine.scan();
    }
}
