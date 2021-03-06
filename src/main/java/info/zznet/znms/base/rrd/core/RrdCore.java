package info.zznet.znms.base.rrd.core;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.entity.RrdDataInfo;
import info.zznet.znms.base.rrd.conf.RrdConfig;
import info.zznet.znms.base.rrd.conf.RrdDsDef;
import info.zznet.znms.base.rrd.conf.RrdTemplate;
import info.zznet.znms.base.rrd.exception.RrdConfigException;
import info.zznet.znms.spider.Engine;
import info.zznet.znms.spider.bean.ScanResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import info.zznet.znms.spider.constants.SnmpConstants;
import info.zznet.znms.web.util.ConfigUtil;
import org.apache.commons.lang.StringUtils;
import org.rrd4j.core.DsDef;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDef;
import org.rrd4j.core.Sample;
import org.springframework.stereotype.Component;

/**
 * rrd核心
 * 负责自动从数据通道采集数据存入rrd
 */
@Component
public class RrdCore {

	private static final double xff = 0.5;
	
	private static final int EXECUTE_THREAD_NUM = 4;
	
	private static final Object lock = new Object();
	
	/**
	 * 分钟
	 */
	public static final int MINUTE = 1;
	/**
	 * 五分钟
	 */
	public static final int FIVE_MINUTE = MINUTE *5;
	/**
	 * 半小时
	 */
	public static final int HALF_HOUR = MINUTE *30;
	/**
	 * 两小时
	 */
	public static final int TWO_HOUR = MINUTE *60*2;
	/**
	 * 天
	 */
	public static final int DAY = MINUTE *60*24;
	
	Map<String, RrdTemplate> rrdTemplateMap = new HashMap<String, RrdTemplate>();
	Map<String, RrdDb> rrdDbMap = new HashMap<String, RrdDb>();

	public void init() {
		// 初始化模板
		rrdTemplateMap = RrdTool.getRrdTemplates();
		// 初始化库
		for(String templateName : rrdTemplateMap.keySet()){
			RrdTemplate template = rrdTemplateMap.get(templateName);
			try {
				template.checkParam();
			} catch (RrdConfigException e) {
				ZNMSLogger.warn("Rrd[" + template.toString() + "] will be skipped, reason:" + e.getMessage());
				continue;
			}				
			
			List<RrdDataInfo> rrdIdList = RrdTool.getRrdDataInfoList(templateName);
			for(RrdDataInfo rrdInfo : rrdIdList){
				RrdDb rrdDb = createRrdDb(templateName, rrdInfo.getRrdDataId());
				if(rrdDb != null){
					rrdDbMap.put(templateName+"_"+rrdInfo.getRrdDataId(), rrdDb);
				}
			}
		}
		// 启动处理器
		Executors.newFixedThreadPool(EXECUTE_THREAD_NUM).submit(new RrdCoreExecutor());
	}
	
	/**
	 * 创建库/得到库对象
	 * @param templateName
	 * @param rrdDataId
	 * @return
	 */
	public RrdDb createRrdDb(String templateName, String rrdDataId){
		RrdConfig rrdConfig = new RrdConfig(rrdDataId, rrdTemplateMap.get(templateName));
		RrdDef rrdDef = getRrdDef(rrdConfig);
		if(rrdDef == null){
			return null;
		}
		RrdDb rrdDb = null;
		synchronized(lock){
			try {
				rrdDb = new RrdDb(rrdDef.getPath(), false);
			} catch(FileNotFoundException e){
				try {
					File base = new File(ConfigUtil.getString("znms.rdd.path"));
					if(!base.exists() || !base.isDirectory()){
						base.mkdirs();
					}
					rrdDb = new RrdDb(rrdDef);
				} catch (IOException e1) {
					ZNMSLogger.error(e1);
				}					
				return null;
			} catch (IOException e) {
				ZNMSLogger.error(e);
			}
		}
		return rrdDb;
	}

	/**
	 * 获取rrd表定义
	 * @param rrdConfig
	 * @return
	 */
	private RrdDef getRrdDef(RrdConfig rrdConfig) {
		RrdTemplate template = rrdConfig.getTemplate();
		if(template == null){
			return null;
		}
		RrdDef rrdDef = new RrdDef(RrdTool.getRrdFileFullPath(template.getRrdTemplateName(), rrdConfig.getDataId()), 60);
		List<RrdDsDef> dsDefList = template.getDsDefList();
		for (RrdDsDef dsDef : dsDefList) {
			rrdDef.addDatasource(dsDef);
		}
		
		// 每1step/1分钟持久化一次，保留1小时
		if (template.getHourly()) {
			rrdDef.addArchive(template.getCf(), xff, MINUTE, 60);
		}
		// 每5step/5分钟持久化一次，保留1天
		if (template.getDaily()) {
			rrdDef.addArchive(template.getCf(), xff, FIVE_MINUTE, 12 * 24);
		}
		// 每30step/30分钟持久化一次，保留1周
		if (template.getWeekly()) {
			rrdDef.addArchive(template.getCf(), xff, HALF_HOUR, 2 * 24 * 7);
		}
		// 每120step/2小时持久化一次，保留1月
		if (template.getMonthly()) {
			rrdDef.addArchive(template.getCf(), xff, TWO_HOUR, 12 * 31);
		}
		// 每1440step/天持久化一次，保留1年
		if (template.getYearly()) {
			rrdDef.addArchive(template.getCf(), xff, DAY, 366);
		}
		return rrdDef;
	}
	
	/**
	 * 获取rrd库
	 * @param rrdDataId
	 * @param templateName
	 * @return
	 */
	private RrdDb getRrdDb(String rrdDataId, String templateName){
		RrdDb rrdDb = rrdDbMap.get(getRrdDbKey(rrdDataId, templateName));
		if(rrdDb == null){
			rrdDb = createRrdDb(templateName, rrdDataId);
			if(rrdDb != null){
				rrdDbMap.put(getRrdDbKey(rrdDataId, templateName), rrdDb);
			}
		}
		return rrdDb;
	}
	
	private String getRrdDbKey(String rrdDataId, String templateName){
		return templateName + "_" + rrdDataId;
	}

	/**
	 * 执行器
	 */
	private class RrdCoreExecutor implements Runnable {
		@Override
		public void run() {
				ScanResult scanResult = null;
				while(true){
					try {
						scanResult = Engine.queue.take();
						RrdTemplate rrdTemplate = rrdTemplateMap.get(scanResult.getRrdTemplateName());
					
						RrdDb rrdDb = getRrdDb(scanResult.getRrdDataId(), scanResult.getRrdTemplateName());
						if(rrdDb == null){
							ZNMSLogger.error("cannot acheive rrd db for["+scanResult.getRrdDataId()+","+scanResult.getRrdTemplateName()+"]");
							continue;
						}
						List<String> dsNameList = new ArrayList<String>();
						for(DsDef dsDef : rrdTemplate.getDsDefList()){
							dsNameList.add(dsDef.getDsName());
						}
						
						Sample sample = rrdDb.createSample();
						sample.setTime(System.currentTimeMillis() / 1000);
						
						String[] data = scanResult.getValue();
						for(int i=0, size=dsNameList.size(); i<size; i++){
							if(data[i] == null || !StringUtils.isNumeric(data[i])) {
								sample.setValue(dsNameList.get(i), Double.NaN);	
							} else {
								sample.setValue(dsNameList.get(i), Double.parseDouble(data[i]));		
							}
						}
						sample.update();

						Engine.otherQueue.put(scanResult);
						int otherQueueSize = Engine.otherQueue.size();
						if (otherQueueSize> SnmpConstants.ENGINE_QUEUE_CAPACITY/2){
							ZNMSLogger.info("入队 otherQueue ，大小已超过最大值一半："+otherQueueSize);
						}
					} catch (Exception e) {
						// do nothing
						ZNMSLogger.error(e);
						continue;
					}
				}
		}
	}
}
