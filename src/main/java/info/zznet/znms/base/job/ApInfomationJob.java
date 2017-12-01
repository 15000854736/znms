package info.zznet.znms.base.job;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.dao.ApInformationMapper;
import info.zznet.znms.base.entity.AcOidConfig;
import info.zznet.znms.base.entity.ApInformation;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.spider.bean.ApProperties;
import info.zznet.znms.spider.bean.ScanHost;
import info.zznet.znms.spider.util.SnmpUtil;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.module.apInformation.service.ApInformationService;
import info.zznet.znms.web.module.system.service.HostService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.quartz.DisallowConcurrentExecution;
import org.snmp4j.smi.OID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service("ApInfomationJob")
@DisallowConcurrentExecution
public class ApInfomationJob {
	
	
	@Autowired
	private HostService hostService;
	
	@Autowired
	private ApInformationService apInformationService;
	
	@Autowired
	private ApInformationMapper apInfoMapper;
	
	private WebRuntimeData webRuntimeData = WebRuntimeData.instance;
	
	public static Map<String,ApInformation> apInfomationMap=new ConcurrentHashMap<String, ApInformation>();
	
	@Scheduled(initialDelay =30*1000,fixedDelay = 5*60*1000)
	public void updateApInfomation(){
		List<Host> hostList = hostService.findAllAc();
		ZNMSLogger.info("before update size:"+apInfomationMap.size());
		int apTotalOnlineNumber=0;
		Integer apCount = 0;
		Integer missingCount = 0;
		for (Host host : hostList) {
			AcOidConfig  acOidConfig =webRuntimeData.getAcOidConfig(host.getAcBrand());
			ScanHost scanHost=new ScanHost();
			scanHost.setCommunity(host.getSnmpCommunity());
			scanHost.setSnmpUserName(host.getSnmpUserName());
			scanHost.setSnmpAuthProtocol(host.getSnmpAuthProtocol());
			scanHost.setSnmpPrivPassphrase(host.getSnmpPrivPassphrase());
			scanHost.setSnmpVersion(host.getSnmpVersion()+"");
			scanHost.setIp(host.getHostIp());
			scanHost.setSnmpPort(host.getSnmpPort()+"");
			scanHost.setSnmpPassword(host.getSnmpPassword());
			scanHost.setSnmpUserName(host.getSnmpUserName());
			OID[] columns =new OID[2];
			//mac
			columns[0] = new OID (acOidConfig.getAcMac());
			//当前在线人数
			columns[1] = new OID(acOidConfig.getAcOnlineNum());

			List<ApProperties> apResult=SnmpUtil.getApInfo(scanHost,columns);
			for (ApProperties apProperties : apResult) {
				apCount++;
				try{
					String apMac = apProperties.getApMac().trim();
					Integer userCount = Integer.parseInt(apProperties.getOnLineNum().trim());;
					ApInformation apInformation =apInfomationMap.get(apMac);
					if(apInformation!=null){
						apInformation.setApUserCount(userCount);
						apTotalOnlineNumber+=userCount;
						apInformation.setLastUpdateTime(new java.util.Date());
						apInfomationMap.put(apMac, apInformation);
					}else{
						missingCount++;
					  	ZNMSLogger.info("ap mac["+apMac+"] not exist in apMap");
					}
				}catch (Exception e){
				  ZNMSLogger.error(e);
				}

			}
		}
		ZNMSLogger.info("finished.ap["+apCount+"],missing["+missingCount+"],userCount["+apTotalOnlineNumber+"]");
		webRuntimeData.setApTotalOnlineNumber(apTotalOnlineNumber);
		webRuntimeData.setApCount(apCount);
		ZNMSLogger.info("after update size:"+apInfomationMap.size());
	}

	@Scheduled(cron = "0 0 22 * * ?")
	public void updateApInfomationDaily(){
		List<Host> hostList = hostService.findAllAc();
		List apList=new ArrayList();
		for (Host host : hostList) {
			scanAc(host,apList);
	  	}
		ZNMSLogger.info("apList size:"+apList.size());
		ZNMSLogger.info("apMap size:"+apInfomationMap.size());
		Iterator<Map.Entry<String, ApInformation>> it = apInfomationMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, ApInformation> entry = it.next();
			boolean flag= apList.contains(entry.getKey());
			if(!flag){
				ZNMSLogger.info("remove ap from map:"+entry.getKey());
				it.remove();
			}
		}
		ZNMSLogger.info("apMap size after remove:"+apInfomationMap.size());
		apList.clear();
	}

	public void scanAc(Host host,List<String> apList) {
		try{
			ScanHost scanHost=new ScanHost();
			scanHost.setCommunity(host.getSnmpCommunity());
			scanHost.setSnmpUserName(host.getSnmpUserName());
			scanHost.setSnmpAuthProtocol(host.getSnmpAuthProtocol());
			scanHost.setSnmpPrivPassphrase(host.getSnmpPrivPassphrase());
			scanHost.setSnmpVersion(host.getSnmpVersion()+"");
			scanHost.setIp(host.getHostIp());
			scanHost.setSnmpPort(host.getSnmpPort()+"");
			scanHost.setSnmpPassword(host.getSnmpPassword());
			scanHost.setSnmpUserName(host.getSnmpUserName());
			OID[] columns =new OID[5];
			AcOidConfig  acOidConfig =null;
			if(host.getAcBrand()!=null){
				  acOidConfig =webRuntimeData.getAcOidConfig(host.getAcBrand());
			}else{
				 acOidConfig =webRuntimeData.getAcOidConfig(AcOidConfig.RUIJIE);
			}
			//mac
			columns[0] = new OID (acOidConfig.getAcMac());
			//IP
			columns[1] = new OID(acOidConfig.getAcIp());
			//当前在线人数
			columns[2] = new OID(acOidConfig.getAcOnlineNum());
			//最大在线人数
			columns[3] = new OID(acOidConfig.getAcMaxOnlineNum());
			//ap name
			columns[4] = new OID(acOidConfig.getAcName());

			List<ApProperties> apResult= SnmpUtil.getApInfo(scanHost,columns);
			for (ApProperties apProperties : apResult) {
				ApInformation apInformation = apInformationService.findApByApMac(apProperties.getApMac().trim());
				if(apInformation!=null){
					apInformation.setApUserCount(Integer.parseInt(apProperties.getOnLineNum().trim()));
					apInformation.setApUserMax(Integer.parseInt(apProperties.getOnLineMaxNum().trim()));
					apInformation.setLastUpdateTime(new java.util.Date());
					apInformation.setApIp(apProperties.getApIp());
					apInformationService.updateByPrimaryKeySelective(apInformation);
				}else{
					apInformation=new  ApInformation();
					apInformation.setApInformationUuid(UUIDGenerator.getGUID());
					apInformation.setApMac(apProperties.getApMac().trim());
					apInformation.setApIp(apProperties.getApIp());
					apInformation.setAcIp(host.getHostIp());
					apInformation.setApUserCount(Integer.parseInt(apProperties.getOnLineNum().trim()));
					apInformation.setApUserMax(Integer.parseInt(apProperties.getOnLineMaxNum().trim()));
					apInformation.setLastUpdateTime(new java.util.Date());
					apInformation.setApName(apProperties.getApName());
					apInformationService.addApInformation(apInformation);
					ZNMSLogger.info("add ap:mac["+apInformation.getApMac()+"],userCount["+apInformation.getApUserCount()+"]");
				}
				apList.add(apInformation.getApMac().trim());
				apInfomationMap.put(apInformation.getApMac().trim(), apInformation);
			}
		}catch (Exception e){
			ZNMSLogger.error(e);
		}
	}
	
	public void init() {
		updateApInfomation();
		List<ApInformation> apInfoList = apInfoMapper.findAll();
		if (apInfoList != null) {
			for (ApInformation apInfo : apInfoList) {
				apInfomationMap.put(apInfo.getApMac().trim(), apInfo);
			}
		}
		ZNMSLogger.info("apMap size after init:"+apInfomationMap.size());
	}

}
