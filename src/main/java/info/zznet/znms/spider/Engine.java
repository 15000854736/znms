package info.zznet.znms.spider;

import info.zznet.znms.base.bean.Content;
import info.zznet.znms.base.bean.HostMessage;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.dao.SystemOptionMapper;
import info.zznet.znms.base.dao.ThresholdValueMapper;
import info.zznet.znms.base.dao.ThresholdValueTriggerLogMapper;
import info.zznet.znms.base.entity.ThresholdValue;
import info.zznet.znms.base.entity.ThresholdValueTriggerLog;
import info.zznet.znms.base.rrd.core.RrdFetcher;
import info.zznet.znms.base.util.DateUtil;
import info.zznet.znms.base.util.MailUtil;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.spider.bean.ScanHost;
import info.zznet.znms.spider.bean.ScanResult;
import info.zznet.znms.spider.constants.HostTypeMap;
import info.zznet.znms.spider.constants.SnmpConstants;
import info.zznet.znms.spider.util.PingUtil;
import info.zznet.znms.spider.util.SnmpUtil;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.module.system.bean.SystemOptionBean;
import info.zznet.znms.web.module.system.service.HostService;
import info.zznet.znms.web.util.ApiClientUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import info.zznet.znms.web.util.ConfigUtil;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.conn.util.InetAddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shenqilei on 2016/9/21.
 */
@Service
public class Engine {

	@Autowired
	private ThresholdValueTriggerLogMapper logMapper;
	
	@Autowired
	private HostService hostService;

	private WebRuntimeData webRuntimeData = WebRuntimeData.instance;

    public static final Map<String,ScanHost> hosts = new ConcurrentHashMap<>(1000);

    private static final ExecutorService scanExecutor = Executors.newFixedThreadPool(8);

    private static final ExecutorService checkExecutor = Executors.newFixedThreadPool(8);
    
    private static final ExecutorService thresholdValueExecutor = Executors.newFixedThreadPool(4);

    public static final LinkedBlockingQueue<ScanResult> queue = new LinkedBlockingQueue<>(SnmpConstants.ENGINE_QUEUE_CAPACITY);
    
    public static final LinkedBlockingQueue<ScanResult> otherQueue = new LinkedBlockingQueue<>(SnmpConstants.ENGINE_QUEUE_CAPACITY);


    public void scan(){
        Iterator<Map.Entry<String,ScanHost>> it = hosts.entrySet().iterator();
        while (it.hasNext()){
            final Map.Entry<String,ScanHost> entry = it.next();
            Runnable scanTask = new Runnable() {
                @Override
                public void run() {
                    List<ScanResult> results = SnmpUtil.getResult(entry.getValue());
                    for (ScanResult scanResult:results){
                        try {
                            queue.put(scanResult);
                            int queueSize = queue.size();
                            if (queueSize>SnmpConstants.ENGINE_QUEUE_CAPACITY/2){
                                ZNMSLogger.info("入队，大小已超过最大值一半："+queueSize);
                            }
                        } catch (InterruptedException e) {
                            ZNMSLogger.error(e);
                        }
                    }
                }
            };
            scanExecutor.submit(scanTask);
        }
    }

    public void check(){
        Iterator<Map.Entry<String,ScanHost>> it = hosts.entrySet().iterator();
        while (it.hasNext()){
            final Map.Entry<String,ScanHost> entry = it.next();
			if(StringUtils.equals(SnmpConstants.HOST_STATUS_UP,entry.getValue().getStatus())){
				Runnable checkTask = new Runnable() {
					@Override
					public void run() {
						ScanHost scanHost = entry.getValue();
						String ip = scanHost.getIp();
						boolean isReachable = false;
						try{
							if(InetAddressUtils.isIPv4Address(ip)){
								//目前只支持ipv4
								switch (scanHost.getAvailabilityMethod()) {
									case 1:
										if(StringUtils.equals(SnmpConstants.SNMP_VERSION_3,scanHost.getSnmpVersion())){
											isReachable = SnmpUtil.availabilityMethodV3(scanHost);
										}else{
											isReachable = SnmpUtil.availabilityMethodV2V1(scanHost);
										}
										break;
									case 2:
										isReachable = PingUtil.ping(ip);
										break;
								}
								if(!isReachable){
									isReachable = PingUtil.isReachable(ip);
								}
							}else{
								isReachable = false;
								ZNMSLogger.warn("主机["+scanHost.getName()+"]，IP["+scanHost.getIp()+"]非法。");
							}
							if (isReachable!=scanHost.isReachable()){
								//可达状态有变
								scanHost.setReachable(isReachable);
								hostService.updateHostWorkStatusByIP(ip, isReachable);
								StringBuilder subject = new StringBuilder();
								String result = isReachable?"恢复正常":"宕机";
								subject.append("主机[").append(scanHost.getName()).append("(").append(scanHost.getIp()).append(")已").append(result);
								//通过接口传递相关信息给微信后台系统
								SystemOptionBean systemOptionBean = webRuntimeData.instance.getSystemOptionBean();
								MailUtil.sendMail(subject.toString(),subject.toString());
								//调用zos接口
								String zosIp = systemOptionBean.getZosIp();
								String zosPort = systemOptionBean.getZosPort();
								if(!StringUtil.isNullString(zosIp) && !StringUtil.isNullString(zosPort)){
									String zosContext = ConfigUtil.getString("zos.context.path","z-os");
									String address = "http://"+zosIp+":"+zosPort+"/"+zosContext+"/api/v1/noEncrypt/znms/switchCrashHostMsg";
									ApiClientUtil apiClientUtil = new ApiClientUtil(address);
									Map<String,String> paramMap = new HashMap<String, String>();
									String type = HostTypeMap.getTypeValueByKey(scanHost.getType());
									String schoolCode = systemOptionBean.getSchoolCode();
									HostMessage message = new HostMessage(isReachable?"up":"down", schoolCode,
											scanHost.getIp(), scanHost.getName(), type, DateUtil.dateToStr(new Date(), DateUtil.DF_yyyyMMddHHmmss));
									paramMap.put("msg", JSONObject.fromObject(message).toString());
									ZNMSLogger.info("传递主机可达信息到Z-OS，["+apiClientUtil.post(paramMap)+"]");;
								}
								ZNMSLogger.info(subject.toString());
							}
							WebRuntimeData.instance.pushPingResult(ip, isReachable);
							ZNMSLogger.debug("主机["+scanHost.getName()+"]，IP["+scanHost.getIp()+"]可达状态["+isReachable+"]");
						}catch (Exception e){
							ZNMSLogger.error(e.getMessage());
						}


					}
				};
				checkExecutor.submit(checkTask);
			}
        }
    }

    public void checkQueueSize(){
        int queueSize = queue.size();
        if (queueSize>SnmpConstants.ENGINE_QUEUE_CAPACITY/2){
            ZNMSLogger.info("队列大小已超过最大值一半："+queueSize);
        }
    }
    
    /**
     * 检查是否触发相关阀值，触发的话，新起一线程来进行后续动作，包括：发送邮件，并记录触发情况等
     * @param scanResult
     */
    public void checkThresholdValueAndOperation(ScanResult scanResult, Map<String, List<ThresholdValue>> map){
    	try {
			List<ThresholdValue> valueList = map.get(scanResult.getRrdDataId());
			if(null!=valueList && valueList.size()>0){
				for(ThresholdValue thresholdValue : valueList){
					if(thresholdValue.getFlowDirection().intValue()==0){
						//cpu、内存等基本类型
						BigDecimal currentThresholodValue = new BigDecimal(scanResult.getValue()[0]).setScale(2,BigDecimal.ROUND_HALF_UP);
						if(currentThresholodValue.compareTo(thresholdValue.getWarningHighThresholdValue())==1
								|| currentThresholodValue.compareTo(thresholdValue.getWarningLowThresholdValue())==-1){
							triggerOperation(thresholdValue, currentThresholodValue);
						}
					}else{
						//从rrd文件中获取
                        double[] traffic = RrdFetcher.fetchLatestData(scanResult.getRrdTemplateName(),scanResult.getRrdDataId());
                        BigDecimal currentThresholodValue = null;
                        if(null!=traffic&&traffic.length==2){
                            if(thresholdValue.getFlowDirection().intValue()==1){
                                //下行
                                currentThresholodValue = new BigDecimal(traffic[1]).setScale(2,BigDecimal.ROUND_HALF_UP);
                            }else{
                                //上行
                                currentThresholodValue = new BigDecimal(traffic[0]).setScale(2,BigDecimal.ROUND_HALF_UP);
                            }
                        }

						if(null!=currentThresholodValue){
                            if(currentThresholodValue.compareTo(thresholdValue.getWarningHighThresholdValue())>0
                                    || currentThresholodValue.compareTo(thresholdValue.getWarningLowThresholdValue())<0){
                                triggerOperation(thresholdValue, currentThresholodValue);
                            }
                        }
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 触发阀值，发送邮件、记录日志
     */
    public void triggerOperation(final ThresholdValue thresholdValue, BigDecimal currentThresholodValue){
    	final ThresholdValue tv = thresholdValue;
    	final BigDecimal ctv = currentThresholodValue;
    	 Runnable thredTask = new Runnable() {
			@Override
			public void run() {
				try {
					
					//记录触发日志
					ThresholdValueTriggerLog log = new ThresholdValueTriggerLog();
					log.setLogUuid(UUIDGenerator.getGUID());
					log.setCreateTime(new Date());
					log.setCurrentValue(ctv);
					log.setHostUuid(tv.getHostUuid());
					log.setThresholdValueUuid(tv.getThresholdValueUuid());
					StringBuffer sb = new StringBuffer();
					sb.append("告警:阀值["+tv.getThresholdValueName()+"]触发,"+
							"由于当前值为["+ctv+"],");
					if(ctv.compareTo(tv.getWarningHighThresholdValue())==1){
						sb.append("已经高于阀值["+tv.getWarningHighThresholdValue()+"]");
						log.setAlarmValue(tv.getWarningHighThresholdValue());
					}else if(ctv.compareTo(tv.getWarningHighThresholdValue())==-1){
						sb.append("已经低于阀值["+tv.getWarningLowThresholdValue()+"]");
						log.setAlarmValue(tv.getWarningLowThresholdValue());
					}
					log.setDescription(sb.toString());
					logMapper.insert(log);
					
					//发送邮件
                    String hostName = tv.getHost().getHostName();
                    String hostIp = tv.getHost().getHostIp();

                    MailUtil.sendMail(hostName+"("+hostIp+")"+"触发阀值："+thresholdValue.getThresholdValueName(),sb.toString());

				} catch (Throwable e) {
					ZNMSLogger.error(e);
				}
			}
    	 };
    	 thresholdValueExecutor.submit(thredTask);
    }
    
}
