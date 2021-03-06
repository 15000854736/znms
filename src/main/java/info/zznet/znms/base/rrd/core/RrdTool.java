package info.zznet.znms.base.rrd.core;

import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.dao.GraphTemplateMapper;
import info.zznet.znms.base.dao.RrdDataInfoMapper;
import info.zznet.znms.base.entity.GraphTemplate;
import info.zznet.znms.base.entity.RrdDataInfo;
import info.zznet.znms.base.rrd.conf.RrdConfig;
import info.zznet.znms.base.rrd.conf.RrdDsDef;
import info.zznet.znms.base.rrd.conf.RrdTemplate;
import info.zznet.znms.base.rrd.exception.RrdExistsException;
import info.zznet.znms.base.util.SpringContextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.zznet.znms.web.util.ConfigUtil;
import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;

/**
 * Rrd工具类
 */
public class RrdTool {
		
	static Map<String, List<RrdDataInfo>> rrdDataInfoMap;
	static Map<String, RrdTemplate> rrdTemplateMap;
	static RrdDataInfoMapper rrdDataListMapper = (RrdDataInfoMapper) SpringContextUtil.getBean("rrdDataInfoMapper");
	static GraphTemplateMapper graphTemplateMapper = (GraphTemplateMapper) SpringContextUtil.getBean("graphTemplateMapper");
	static{
		rrdDataInfoMap = new HashMap<String, List<RrdDataInfo>>();
		rrdTemplateMap = loadTemplates();
	    for(String templateName : rrdTemplateMap.keySet()){
	    	List<RrdDataInfo> rrdDataList = rrdDataListMapper.findByTemplate(templateName);
	    	rrdDataInfoMap.put(templateName, rrdDataList == null?new ArrayList<RrdDataInfo>():rrdDataList);
	    }
	}
		
	/**
	 * 获取rrd模板
	 * @return
	 */
	private static Map<String, RrdTemplate> loadTemplates(){
		Map<String, RrdTemplate> rrdConfigMap = new HashMap<String, RrdTemplate>();
		List<GraphTemplate> list = graphTemplateMapper.findAll();
		if(list != null){
			for(GraphTemplate template : list){
				RrdTemplate _template = new RrdTemplate();
				_template.setRrdTemplateName(template.getGraphTemplateSimpleName());
				_template.setRrdTemplateDesc(template.getGraphTemplateName());
				List<RrdDsDef> dsDefList = new ArrayList<RrdDsDef>();
				DsType dsType = null;
				switch(template.getDsType()){
					case 1:
						dsType = DsType.ABSOLUTE;
						break;
					case 2:
						dsType = DsType.COUNTER;
						break;
					case 3:
						dsType = DsType.DERIVE;
						break;
					case 4:
						dsType = DsType.GAUGE;
						break;
					default:
						dsType = DsType.GAUGE;
						break;
				}
				RrdDsDef dsDef = new RrdDsDef(template.getDsName(), dsType, 120, Long.MIN_VALUE, Long.MAX_VALUE, template.getDsShowName());
				dsDefList.add(dsDef);
				_template.setDsDefList(dsDefList);
				ConsolFun cf = null;
				switch(template.getConsolFun()){
					case 1:
						cf = ConsolFun.AVERAGE;
						break;
					case 2:
						cf = ConsolFun.FIRST;
						break;
					case 3:
						cf = ConsolFun.LAST;
						break;
					case 4:
						cf = ConsolFun.MAX;
						break;
					case 5:
						cf = ConsolFun.MIN;
						break;
					case 6:
						cf = ConsolFun.TOTAL;
						break;
					default:
						cf = ConsolFun.AVERAGE;
						break;
				}
				_template.setCf(cf);
				rrdConfigMap.put(template.getGraphTemplateSimpleName(), _template);
			}
			
			RrdTemplate template = new RrdTemplate();
			template.setRrdTemplateName(SystemConstants.TEMPLATE_NAME_NET_STREAM);
			template.setRrdTemplateDesc("网络流量");
			template.setCf(ConsolFun.AVERAGE);
			List<RrdDsDef> dsDefList = new ArrayList<RrdDsDef>();
			RrdDsDef upDsDef = new RrdDsDef(SystemConstants.DATA_SOURCE_NAME_UP, DsType.COUNTER, 120, Long.MIN_VALUE, Long.MAX_VALUE, "上行流量");
			dsDefList.add(upDsDef);
			RrdDsDef downDsDef = new RrdDsDef(SystemConstants.DATA_SOURCE_NAME_DOWN, DsType.COUNTER, 120, Long.MIN_VALUE, Long.MAX_VALUE, "下行流量");
			dsDefList.add(downDsDef);
			template.setDsDefList(dsDefList);
			rrdConfigMap.put(template.getRrdTemplateName(), template);
		}
		return rrdConfigMap;
	}
	
	public static Map<String, RrdTemplate> getRrdTemplates(){
		return rrdTemplateMap;
	}
	
	public static RrdTemplate getRrdTemplate(String rrdTemplateName){
		return rrdTemplateMap.get(rrdTemplateName);
	}
	
	/**
	 * 注册为rrd数据
	 * @param templateName
	 * @param rrdDataId
	 */
	public static String registerRrd(String templateName, String rrdDataId) throws RrdExistsException {
		registerRrd(templateName, rrdDataId, null);
		return getRrdFileName(templateName, rrdDataId);
	}
	
	/**
	 * 注册为rrd数据
	 * @param templateName
	 * @param rrdDataId
	 * @param rrdDataName
	 * @throws RrdExistsException
	 */
	public static String registerRrd(String templateName, String rrdDataId, String rrdDataName) throws RrdExistsException {
		List<RrdDataInfo> rrdDataList = getRrdDataInfoList(templateName);
		RrdDataInfo rrdDataInfo = new RrdDataInfo();
		rrdDataInfo.setRrdDataId(rrdDataId);
		rrdDataInfo.setRrdTemplateName(templateName);
		rrdDataInfo.setRrdDataName(rrdDataName);
		if(rrdDataList.contains(rrdDataInfo)){
			throw new RrdExistsException("Rrd data info ["+rrdDataInfo+"] already exists");
		}
		rrdDataList.add(rrdDataInfo);
		rrdDataListMapper.insert(rrdDataInfo);
		RrdCore rrdCore = (RrdCore) SpringContextUtil.getBean("rrdCore");
		rrdCore.createRrdDb(templateName, rrdDataId);
		return getRrdFileName(templateName, rrdDataId);
	}
	
	/**
	 * 获取指定模板下的rrd信息列表
	 * @param templateName
	 */
	public static List<RrdDataInfo> getRrdDataInfoList(String templateName){
		List<RrdDataInfo> rrdIdList = rrdDataInfoMap.get(templateName);
		if(rrdIdList == null){
			return new ArrayList<RrdDataInfo>();
		}
		return rrdDataInfoMap.get(templateName);
	}
	
	/**
	 * 获取指定rrd信息
	 * @param templateName
	 * @param rrdDataId
	 * @return
	 */
	public static RrdDataInfo getRrdDataInfo(String templateName, String rrdDataId){
		List<RrdDataInfo> rrdDataInfoList = getRrdDataInfoList(templateName);
		for(RrdDataInfo rrdDataInfo : rrdDataInfoList){
			if(rrdDataInfo.getRrdDataId().equals(rrdDataId)){
				return rrdDataInfo;
			}
		}
		return null;
	}
	
	/**
	 * 获取rrd文件路径
	 * @param templateName
	 * @param dataId
	 * @return
	 */
	public static String getRrdFileFullPath(String templateName, String dataId) {
		return ConfigUtil.getString("znms.rdd.path") + File.separator + templateName +"_"+ dataId + ".rrd";
	}
	
	/**
	 * 获取rrd文件名
	 * @param templateName
	 * @param dataId
	 * @return
	 */
	public static String getRrdFileName(String templateName, String dataId) {
		return templateName +"_"+ dataId + ".rrd";
	}
	
	/**
	 * 删除rrd数据
	 * @param templateName
	 * @param dataId
	 * @return
	 */
	public static boolean deleteRrd(String dataId) {
		RrdDataInfo rrdDataInfo = rrdDataListMapper.findById(dataId);
		if(rrdDataInfo == null){
			return false;
		}
		File file = new File(getRrdFileFullPath(rrdDataInfo.getRrdTemplateName(), dataId));
		if(file.exists() && file.isFile()) {
			return file.delete();
		}
		rrdDataListMapper.deleteByPrimaryKey(dataId);
		List<RrdDataInfo> rrdDataList = rrdDataInfoMap.get(rrdDataInfo.getRrdTemplateName());
		if(rrdDataList != null){
			rrdDataList.remove(rrdDataInfo);
		}
		return true;
	}
}
