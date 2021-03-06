package info.zznet.znms.base.rrd.core;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.entity.RrdDataInfo;
import info.zznet.znms.base.rrd.bean.Graph;
import info.zznet.znms.base.rrd.bean.SubItem;
import info.zznet.znms.base.rrd.conf.RrdDsDef;
import info.zznet.znms.base.rrd.conf.RrdTemplate;
import info.zznet.znms.base.rrd.exception.RrdQueryException;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.rrd4j.ConsolFun;
import org.rrd4j.core.FetchData;
import org.rrd4j.core.FetchRequest;
import org.rrd4j.core.RrdDb;

/**
 * rrd查询器
 */
public class RrdFetcher {
	
	static final SimpleDateFormat MINUTLY_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	static final SimpleDateFormat HOURLY_SDF = new SimpleDateFormat("yyyy-MM-dd HH");
	static final SimpleDateFormat DAILY_SDF = new SimpleDateFormat("yyyy-MM-dd");
	
	static final DecimalFormat DF = new DecimalFormat("###");

	/**
	 * 获取数据
	 * @param rrdTemplateName
	 * @param rrdDataId
	 * @param startTime
	 * @param endTime
	 * @throws RrdQueryException
	 */
	public static FetchData fetchData(String rrdTemplateName, String rrdDataId, long startTime, long endTime) throws RrdQueryException{
		startTime = startTime >= 10000000000l?startTime/1000:startTime;
		endTime = endTime >= 10000000000l?endTime/1000:endTime;
		if(startTime >= endTime){
			throw new RrdQueryException("开始时间必须小于结束时间");
		}
		
		try {
			File rrdFile = new File(RrdTool.getRrdFileFullPath(rrdTemplateName, rrdDataId));
			if(!rrdFile.exists()){
				return null;
			}
			RrdDb rrdDb = new RrdDb(RrdTool.getRrdFileFullPath(rrdTemplateName, rrdDataId), true);
			FetchRequest request = rrdDb.createFetchRequest(ConsolFun.AVERAGE,
													startTime,
													endTime);
			FetchData fetchData = request.fetchData();
			return fetchData;
		} catch (IOException e) {
			ZNMSLogger.error(e);
			throw new RrdQueryException("发生内部错误");
		}
	}
	
	public static double[] fetchLatestData(String rrdTemplateName, String rrdDataId) {
		try {
			File rrdFile = new File(RrdTool.getRrdFileFullPath(rrdTemplateName, rrdDataId));
			if(!rrdFile.exists()){
				return null;
			}
			RrdDb rrdDb = new RrdDb(RrdTool.getRrdFileFullPath(rrdTemplateName, rrdDataId), true);
			rrdDb.getLastArchiveUpdateTime();
			FetchRequest request = rrdDb.createFetchRequest(ConsolFun.AVERAGE,
					rrdDb.getLastUpdateTime(),
					rrdDb.getLastUpdateTime());
			FetchData fetchData = request.fetchData();
			RrdTemplate rrdTemplate = RrdTool.getRrdTemplate(rrdTemplateName);
			List<RrdDsDef> dsDefList = rrdTemplate.getDsDefList();
			double[] retVal = new double[dsDefList.size()];
			int k = 0;
			for(RrdDsDef dsDef : dsDefList){	
				double[] fetchedValues = fetchData.getValues(dsDef.getDsName());
				int size = fetchedValues.length;
				for(int i=size - 1;i>=0;i--){
					if(i == size - 1 || i == size - 2){
						if(Double.isNaN(fetchedValues[i])) {
							continue;
						}
					}
					if(Double.isNaN(fetchedValues[i])){
						retVal[k] = 0d;
						break;
					} else {
						retVal[k] = fetchedValues[i];
						break;
					}
				}
				k++;
			}
			return retVal;
		} catch (IOException e) {
			ZNMSLogger.error(e);
		}
		return null;
	}
	
	/**
	 * 获取数据（五分钟内）
	 * @param rrdTemplateName
	 * @param rrdDataId
	 * @throws RrdQueryException
	 */
	public static FetchData fetchData(String rrdTemplateName, String rrdDataId) throws RrdQueryException{
		long endTime = System.currentTimeMillis()/1000;
		long startTime = endTime - 60 * 5;
		return fetchData(rrdTemplateName, rrdDataId, startTime, endTime);
	}
	
	/**
	 * 获取数据
	 * @param rrdTemplateName
	 * @param rrdDataId
	 * @param startTime
	 * @param endTime
	 * @throws RrdQueryException
	 */
	public static FetchData fetchData(String rrdTemplateName, String rrdDataId, Date startTime, Date endTime) throws RrdQueryException{
		if(startTime==null||endTime==null){
			throw new RrdQueryException("必须填写起止时间");
		}
		long _startTime = startTime.getTime()/1000;
		long _endTime = endTime.getTime()/1000;
		return fetchData(rrdTemplateName, rrdDataId, _startTime, _endTime);
	}
	
	/**
	 * 获取数据并转换为图表格式（五分钟内）
	 * @param rrdTemplateName
	 * @param rrdDataId
	 * @return
	 * @throws RrdQueryException
	 */
	public static Graph fetchDataAndConvertToGraph(String rrdTemplateName, String rrdDataId) throws RrdQueryException {
		long endTime = System.currentTimeMillis()/1000;
		long startTime = endTime - 60 * 5;
		return fetchDataAndConvertToGraph(rrdTemplateName, rrdDataId, startTime, endTime);
	}
	
	/**
	 * 获取数据并转换为图表格式
	 * @param rrdTemplateName
	 * @param rrdDataId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws RrdQueryException
	 */
	public static Graph fetchDataAndConvertToGraph(String rrdTemplateName, String rrdDataId, Date startTime, Date endTime) throws RrdQueryException {
		if(startTime==null||endTime==null){
			throw new RrdQueryException("必须填写起止时间");
		}
		long _startTime = startTime.getTime()/1000;
		long _endTime = endTime.getTime()/1000;
		return fetchDataAndConvertToGraph(rrdTemplateName, rrdDataId, _startTime, _endTime);
	}
	
	/**
	 * 获取数据并转换为图表格式
	 * @param rrdTemplateName
	 * @param rrdDataId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws RrdQueryException
	 */
	public static Graph fetchDataAndConvertToGraph(String rrdTemplateName, String rrdDataId, long startTime, long endTime) throws RrdQueryException{
		Graph graph = new Graph();
		if(endTime > System.currentTimeMillis()/1000){
			endTime = System.currentTimeMillis()/1000;
		}
		FetchData fetchData = fetchData(rrdTemplateName, rrdDataId, startTime, endTime);
		if(fetchData == null){
			return null;
		}
		RrdTemplate rrdTemplate = RrdTool.getRrdTemplate(rrdTemplateName);
		RrdDataInfo rrdDataInfo = RrdTool.getRrdDataInfo(rrdTemplateName, rrdDataId);
		
		/*****************标题和模板名：start*******************/
		graph.setTemplateName(StringUtils.isEmpty(rrdTemplate.getRrdTemplateDesc())?rrdTemplateName:rrdTemplate.getRrdTemplateDesc());
		graph.setTitle(StringUtils.isEmpty(rrdDataInfo.getRrdDataName())?rrdDataId:rrdDataInfo.getRrdDataName());
		/*****************标题和模板名：end*******************/
		
		/*****************横轴：start*******************/
		long step = fetchData.getArcStep();
		long[] timestamps = fetchData.getTimestamps();
		
		long lastTimeStamp = timestamps[timestamps.length - 1];
		Calendar lastTimeCal = Calendar.getInstance();
		lastTimeCal.setTimeInMillis(lastTimeStamp * 1000);
		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.setTimeInMillis(endTime * 1000);

		SimpleDateFormat sdf;
		if(step == RrdCore.TWO_HOUR){
			sdf = HOURLY_SDF;
		} else if(step == RrdCore.DAY){
			sdf = DAILY_SDF;
		} else {
			sdf =MINUTLY_SDF;
		}
		List<String> xAxis = new ArrayList<String>();
		for(int i =0;i<timestamps.length;i++){
			xAxis.add(sdf.format(new Date(timestamps[i]*1000)));
		}
		
		graph.setxAxis(xAxis);
		/*****************横轴：end*******************/
		
		/*****************分项标题和数据：start*******************/		
		List<RrdDsDef> dsDefList = rrdTemplate.getDsDefList();
		
		List<String> subitemLabels = new ArrayList<String>();
		List<SubItem> data = new ArrayList<SubItem>();
		int removeCount = 0;
		for(RrdDsDef dsDef : dsDefList){
			SubItem subItem = new SubItem();
			if(StringUtils.isEmpty(dsDef.getShowName())){
				subitemLabels.add(dsDef.getDsName());
				subItem.setName(dsDef.getDsName());
			} else {
				subitemLabels.add(dsDef.getShowName());
				subItem.setName(dsDef.getShowName());
			}
			double[] fetchedValues = fetchData.getValues(dsDef.getDsName());
			List<String> value = new ArrayList<String>();
			
			
			for(int i=0,size=fetchedValues.length;i<size;i++){
				if(i == size - 1 || i == size - 2){
					if(Double.isNaN(fetchedValues[i])) {
						removeCount = size - i;
						break;
					}
				}
				if(Double.isNaN(fetchedValues[i])){
					value.add("0");
				} else {
					value.add(DF.format(fetchedValues[i]));
				}
			}
			subItem.setData(value);
			
			
			data.add(subItem);
		}
		
		for(int i=0;i<removeCount;i++){
			xAxis.remove(xAxis.size() - 1);
		}
		graph.setData(data);
		graph.setSubitemLabels(subitemLabels);
		/*****************分项标题和数据：end*******************/
		
		return graph;
	}
	
	
}
