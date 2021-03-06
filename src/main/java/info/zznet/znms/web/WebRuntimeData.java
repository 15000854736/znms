package info.zznet.znms.web;

import info.zznet.znms.base.bean.HostPingResult;
import info.zznet.znms.base.bean.ZlogSystemStatus;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.dao.AcOidConfigMapper;
import info.zznet.znms.base.dao.HostMapper;
import info.zznet.znms.base.dao.ImportResultMapper;
import info.zznet.znms.base.dao.SystemLogDeleteRuleMapper;
import info.zznet.znms.base.dao.SystemOptionMapper;
import info.zznet.znms.base.entity.AcOidConfig;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.entity.SystemLogDeleteRule;
import info.zznet.znms.base.util.SpringContextUtil;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.screen.bean.OnlineUserInfo;
import info.zznet.znms.web.module.screen.bean.UrlRanking;
import info.zznet.znms.web.module.system.bean.ImportTask;
import info.zznet.znms.web.module.system.bean.SystemOptionBean;
import info.zznet.znms.web.util.ConfigUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * 运行期数据
 * @author dell001
 *
 */
public enum WebRuntimeData {
	instance;
	
	private SystemOptionBean systemOptionBean;
	
	private Map<String, SystemLogDeleteRule> ruleMap;
	
	private Map<String, Host> hostMap;
	
	private HostMapper hostMapper;
	
	private ImportResultMapper importResultMapper;
	
	private LinkedBlockingQueue<ImportTask> importTaskQueue;
	
	private ImportTask runningImportTask;
	
	private ImportTaskExecutor taskExecutor;
	
	private OnlineUserInfo onlineUserInfo;
	
	private List<UrlRanking> urlRankingList;
	
	private Map<String, HostPingResult> pingResultMap;
	
	private Map<String, AcOidConfig> acOidConfigMap;
	
	private AcOidConfigMapper acOidConfigMapper;
	
	private long badStatusHostCount = 0l;
	
	
	/*
	 * 间隔五分钟
	 */
	private ConcurrentLinkedQueue<ZlogSystemStatus> zlogSystemStatusList;
	private List<Long> zlogNatlogCountList;
	private List<Long> zlogUrlLogCountList;
	
	private KafkaProducer<String, String> producer;
	
	private int networkHealth = 0;
	
	/**
	 * zlog传递过来的大图显示数据
	 */
	private String screenData;
	
	private String userTerminalData;
	private String flowData;
	
	/**
	 * ap在线人数总和
	 */
	private int apTotalOnlineNumber;

	/**
	 * ap数量
	 */
	private int apCount;
	
	private WebRuntimeData(){
		reloadSystemOptionBean();
		
		SystemLogDeleteRuleMapper ruleMapper = (SystemLogDeleteRuleMapper) SpringContextUtil.getBean("systemLogDeleteRuleMapper");
		Pager pager = new Pager();
		pager.setLimit(Integer.MAX_VALUE);
		pager.setOffset(0);
		List<SystemLogDeleteRule> ruleList = ruleMapper.findPageList(pager, null);
		ruleMap = new ConcurrentHashMap<String, SystemLogDeleteRule>();
		if(ruleList != null){
			for(SystemLogDeleteRule rule : ruleList){
				ruleMap.put(rule.getSystemLogDeleteRuleUuid(), rule);
			}
		}
		
		hostMapper = (HostMapper) SpringContextUtil.getBean("hostMapper");
		List<Host> hostList = hostMapper.findAll();
		hostMap = new ConcurrentHashMap<String, Host>();
		if(hostList != null){
			for(Host host : hostList) {
				saveHost(host);
			}
		}		
		acOidConfigMapper = (AcOidConfigMapper) SpringContextUtil.getBean("acOidConfigMapper");
		List<AcOidConfig> acOidConfigList = acOidConfigMapper.findAll();
		acOidConfigMap = new ConcurrentHashMap<String, AcOidConfig>();
		if(acOidConfigList != null){
			for(AcOidConfig acOidConfig : acOidConfigList) {
				saveAcOidConfig(acOidConfig);
			}
		}		
		
		importResultMapper = (ImportResultMapper) SpringContextUtil.getBean("importResultMapper");
		
		importTaskQueue = new LinkedBlockingQueue<ImportTask>(10);
		taskExecutor = new ImportTaskExecutor();
		taskExecutor.start();
		
		pingResultMap = new HashMap<String, HostPingResult>();
		
		producer = createProducer();
		
		zlogSystemStatusList = new ConcurrentLinkedQueue<ZlogSystemStatus>();
		for(int i=0;i<12;i++){
			zlogSystemStatusList.add(new ZlogSystemStatus());
		}
		
		zlogNatlogCountList = new ArrayList<Long>();
		zlogUrlLogCountList = new ArrayList<Long>();
		for(int i=0;i<8;i++){
			zlogNatlogCountList.add(0l);
			zlogUrlLogCountList.add(0l);
		}
		
	}

	public void pushPingResult(String hostIp, boolean result) {
		HostPingResult pingResult = pingResultMap.get(hostIp);
		if(pingResult == null) {
			pingResult = new HostPingResult();
			pingResult.setResultList(new ConcurrentLinkedQueue<Boolean>());
			pingResultMap.put(hostIp, pingResult);
		}
		ConcurrentLinkedQueue<Boolean> queue = pingResult.getResultList();
		if(!result) {
			pingResult.setFail(pingResult.getFail() + 1);
		} else {
			pingResult.setSuccess(pingResult.getSuccess() + 1);
		}
		if(queue.size() > 60) {
			boolean frontResult = queue.poll();
			if(!frontResult) {
				pingResult.setFail(pingResult.getFail() - 1);
			} else {
				pingResult.setSuccess(pingResult.getSuccess() - 1);
			}
		}
	}
	
	public Map<String, HostPingResult> getPingResult(){
		return pingResultMap;
	}
	
	public void pushZlogSystemStatus(ZlogSystemStatus systemStatus){
		if(zlogSystemStatusList.size() >= 12) {
			zlogSystemStatusList.poll();
		}
		zlogSystemStatusList.add(systemStatus);
	}
	
	public ConcurrentLinkedQueue<ZlogSystemStatus> getZlogSystemStatusList(){
		return zlogSystemStatusList;
	}
	
	/**
	 * 获取系统配置
	 * @return
	 */
	public SystemOptionBean getSystemOptionBean() {
		return systemOptionBean;
	}
	
	public void reloadSystemOptionBean(){
		SystemOptionMapper systemOptionMapper = (SystemOptionMapper) SpringContextUtil.getBean("systemOptionMapper");
		systemOptionBean = new SystemOptionBean(systemOptionMapper.findAll());
	}
	
	/**
	 * 获取日志删除规则列表
	 * @return
	 */
	public List<SystemLogDeleteRule> getRules(){
		return new ArrayList<SystemLogDeleteRule>(ruleMap.values());
	}
	
	/**
	 * 删除规则
	 * @param ids
	 */
	public void deleteRule(String... ids){
		if(ids != null){
			for(String id : ids){
				ruleMap.remove(id);				
			}
		}
	}
	
	/**
	 * 保存规则
	 * @param rule
	 */
	public void saveRule(SystemLogDeleteRule... rule){
		if(rule == null){
			return;
		}
		for(SystemLogDeleteRule _rule : rule){
			ruleMap.put(_rule.getSystemLogDeleteRuleUuid(), _rule);			
		}
	}
	
	/**
	 * 禁用/激活规则
	 * @param activate
	 * @param uuid
	 */
	public void toggleRule(boolean activate, String uuid){
		SystemLogDeleteRule rule = ruleMap.get(uuid);
		if(rule != null){
			rule.setRuleStatus(activate?1:0);
		}
	}
	
	/**
	 * 保存主机
	 * @param hosts
	 */
	public void saveHost(Host...hosts){
		if(hosts == null){
			return;
		}
		for(Host host : hosts){
			hostMap.put(host.getHostIp(), host);			
		}
	}
	
	/**
	 * 删除主机
	 * @param ips
	 */
	public void deleteHost(String... ips){
		if(ips != null){
			for(String ip : ips){
				hostMap.remove(ip);				
			}
		}
	}
	
	/**
	 * 获得主机
	 * @param ip
	 * @return
	 */
	public Host getHost(String ip){
		return hostMap.get(ip);
	}
	
	/**
	 * 添加AC OID配置
	 * @param acOidConfigs
	 */
	public void saveAcOidConfig(AcOidConfig...acOidConfigs){
		if(acOidConfigs == null){
			return;
		}
		for(AcOidConfig acOidConfig : acOidConfigs){
			acOidConfigMap.put(acOidConfig.getAcBrand(), acOidConfig);			
		}
	}
	/**
	 * 获取ACOID配置
	 * @param code
	 * @return
	 */
	public AcOidConfig getAcOidConfig(String code){
		if(StringUtil.isNullString(code)){
			return acOidConfigMap.get(AcOidConfig.RUIJIE);
		}else{
			return acOidConfigMap.get(code);
		}
	}
	/**
	 * 删除AC OID配置
	 * @param code
	 */
	public void deleteAcOidConfig(String code){
		acOidConfigMap.remove(code);
	}
	
	/**
	 * 获取导入任务队列
	 * @return
	 */
	public LinkedBlockingQueue<ImportTask> getTaskQueue(){
		return importTaskQueue;
	}
	
	/**
	 * 获取执行中导入任务
	 * @return
	 */
	public ImportTask getRunningTask(){
		return runningImportTask;
	}
	
	/**
	 * 添加导入任务
	 * @param file
	 * @param id
	 * @param adminName
	 * @return
	 */
	public boolean addImportTask(File file, String id, String adminName){
		ImportTask task = new ImportTask();
		task.setSourceFile(file);
		task.setId(id);
		task.setAdminName(adminName);
		task.setRegisterTime(new Date());
		if(importTaskQueue.size()>=10) {
			return false;
		} else {
			importTaskQueue.add(task);
			return true;
		}
	}
	
	public OnlineUserInfo getOnlineUserInfo() {
		return onlineUserInfo;
	}

	public void setOnlineUserInfo(OnlineUserInfo onlineUserInfo) {
		this.onlineUserInfo = onlineUserInfo;
	}

	public List<UrlRanking> getUrlRankingList() {
		return urlRankingList;
	}

	public void setUrlRankingList(List<UrlRanking> urlRankingList) {
		this.urlRankingList = urlRankingList;
	}

	public int getNetworkHealth() {
		return networkHealth;
	}

	public void setNetworkHealth(int networkHealth) {
		this.networkHealth = networkHealth;
	}

	public List<Long> getZlogNatlogCountList() {
		return zlogNatlogCountList;
	}

	public void setZlogNatlogCountList(List<Long> zlogNatlogCountList) {
		this.zlogNatlogCountList = zlogNatlogCountList;
	}

	public List<Long> getZlogUrlLogCountList() {
		return zlogUrlLogCountList;
	}

	public void setZlogUrlLogCountList(List<Long> zlogUrlLogCountList) {
		this.zlogUrlLogCountList = zlogUrlLogCountList;
	}


	public long getBadStatusHostCount() {
		return badStatusHostCount;
	}

	public void setBadStatusHostCount(long badStatusHostCount) {
		this.badStatusHostCount = badStatusHostCount;
	}
	
	public String getScreenData() {
		return screenData;
	}

	public void setScreenData(String screenData) {
		this.screenData = screenData;
	}
	
	
	public String getUserTerminalData() {
		return userTerminalData;
	}

	public void setUserTerminalData(String userTerminalData) {
		this.userTerminalData = userTerminalData;
	}

	public String getFlowData() {
		return flowData;
	}

	public void setFlowData(String flowData) {
		this.flowData = flowData;
	}
	
	
	public int getApTotalOnlineNumber() {
		return apTotalOnlineNumber;
	}

	public void setApTotalOnlineNumber(int apTotalOnlineNumber) {
		this.apTotalOnlineNumber = apTotalOnlineNumber;
	}

	public int getApCount() {
		return apCount;
	}

	public void setApCount(int apCount) {
		this.apCount = apCount;
	}

	public void refreshProducer(){
		try {
			if(producer != null) {
				producer.flush();
				producer.close();
			}
		} catch (Exception e){
			ZNMSLogger.debug(e.getMessage());
		}
		producer = createProducer();
	}

	private KafkaProducer<String, String> createProducer() {
		if(StringUtils.isEmpty(systemOptionBean.getKafkaIp())) {
			return null;
		}
		Properties properties = new Properties();
		properties.put("group.id", "znms");
		properties.put("bootstrap.servers", systemOptionBean.getKafkaIp()+":9092");
		properties.put("acks", ConfigUtil.getString("acks", "all"));
		properties.put("retries", ConfigUtil.getString("retries", "0"));
		properties.put("batch.size", ConfigUtil.getString("batch.size", "10000"));
		properties.put("auto.commit.interval.ms", ConfigUtil.getString("auto.commit.interval.ms", "10000"));
		properties.put("linger.ms", ConfigUtil.getString("linger.ms", "0"));
		properties.put("key.serializer", ConfigUtil.getString("key.serializer", "org.apache.kafka.common.serialization.StringSerializer"));
		properties.put("value.serializer", ConfigUtil.getString("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"));
		properties.put("block.on.buffer.full", ConfigUtil.getString("block.on.buffer.full", "false"));
		properties.put("producer.type", ConfigUtil.getString("producer.type", "async"));
		properties.put("max.block.ms", 50);
		properties.put("block.on.buffer.full", false);
		
		KafkaProducer<String, String> _producer = new KafkaProducer<>(properties);
		return _producer;
	}
	
	public KafkaProducer<String, String> getProducer(){
		return producer;
	}

	public class ImportTaskExecutor extends Thread {
		
		private boolean running = true;
		public void run(){
			while(running) {
				try {
					runningImportTask = importTaskQueue.take();
					if(runningImportTask != null){
						runningImportTask.doImport();
						runningImportTask = null;
					}
				} catch (Exception e) {
					ZNMSLogger.error(e);
					continue;
				}
			}
		}
		
		public void shutdown(){
			running = false;
		}
	}
}
