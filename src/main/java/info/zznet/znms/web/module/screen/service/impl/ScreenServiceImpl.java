package info.zznet.znms.web.module.screen.service.impl;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.dao.ScreenMapper;
import info.zznet.znms.base.entity.Screen;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.screen.service.ScreenService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by shenqilei on 2016/11/29.
 */
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("screenService")
public class ScreenServiceImpl extends BaseServiceImpl implements ScreenService{

    @Autowired
    private ScreenMapper screenMapper;
    
	private WebRuntimeData webRuntimeData = WebRuntimeData.instance;


    @Override
    public List<Screen> findAll() {
        return screenMapper.findAll();
    }


	@Override
	public Screen findById(String id) {
		return screenMapper.selectByPrimaryKey(id);
	}


	@Override
	public int addScreen(Screen screen) {
		return screenMapper.insert(screen);
	}


	@Override
	public int deleteById(String id) {
		return screenMapper.deleteByPrimaryKey(id);
	}


	@Override
	public int updateByIdSelective(Screen screen) {
		return screenMapper.updateByPrimaryKeySelective(screen);
	}

	@Override
	public int updateScreen(Screen screen) {
		return screenMapper.updateByPrimaryKey(screen);
	}
	
	@Override
	public List<Screen> findByName(String name){
		return screenMapper.findByName(name);
	}
	
	@Override
	public List<Screen> findByCode(String code){
		return screenMapper.findByCode(code);
	}


	@Override
	public String findStudentTeacherAccessTypeData(String type) {
		try {
			//String zlogresponse =  webRuntimeData.getScreenData();
			String zlogresponse = "{\"onlineDetail\":{\"教师\":{\"etcOnlineTimePerDay\":0,\"etcOnlineTimePerRecord\":0,\"pcOnlineTimePerDay\":7.080268,\"pcOnlineTimePerRecord\":6.076185,\"wirelessOnlineTimePerDay\":23.575,\"wirelessOnlineTimePerRecord\":15.716666},\"学生\":{\"etcOnlineTimePerDay\":0,\"etcOnlineTimePerRecord\":0,\"pcOnlineTimePerDay\":7.080268,\"pcOnlineTimePerRecord\":6.076185,\"wirelessOnlineTimePerDay\":23.575,\"wirelessOnlineTimePerRecord\":15.716666}},\"flow\":{\"教师\":{\"etcFlowPerDay\":0,\"etcFlowPerRecord\":0,\"pcFlowPerDay\":0,\"pcFlowPerRecord\":0,\"wirelessFlowPerDay\":0,\"wirelessFlowPerRecord\":0},\"学生\":{\"etcFlowPerDay\":0,\"etcFlowPerRecord\":0,\"pcFlowPerDay\":0,\"pcFlowPerRecord\":0,\"wirelessFlowPerDay\":0,\"wirelessFlowPerRecord\":0}},\"accessTypeCount\":{\"学生\":{\"有线1x接\":1000,\"无线1x接\":2000,\"有线portal接\":300,\"无线portal接\":2000},\"教师\":{\"有线1x接\":10,\"无线1x接\":20,\"有线portal接\":30,\"无线portal接\":50}},\"accessTypeTrend\":{\"学生\":{\"1481676940000\":{\"有线1x接\":10,\"无线1x接\":20,\"有线portal接\":100,\"无线portal接\":50},\"1481582580000\":{\"有线1x接\":30,\"无线1x接\":50,\"有线portal接\":200,\"无线portal接\":80},\"1481560980000\":{\"有线1x接\":50,\"无线1x接\":200,\"有线portal接\":200,\"无线portal接\":80},\"1481566140000\":{\"有线1x接\":50,\"无线1x接\":30,\"有线portal接\":3000,\"无线portal接\":800},\"1481634360000\":{\"有线1x接\":60,\"无线1x接\":70,\"有线portal接\":200,\"无线portal接\":600},\"1481641440000\":{\"有线1x接\":100,\"无线1x接\":200,\"有线portal接\":400,\"无线portal接\":500},\"1481576220000\":{\"有线1x接\":30,\"无线1x接\":20,\"有线portal接\":550,\"无线portal接\":80},\"1481638380000\":{\"有线1x接\":0,\"无线1x接\":0,\"有线portal接\":300,\"无线portal接\":700}},\"教师\":{\"1481576940000\":{\"有线1x接\":100,\"无线1x接\":200,\"有线portal接\":300,\"无线portal接\":2000},\"1481582580000\":{\"有线1x接\":200,\"无线1x接\":400,\"有线portal接\":500,\"无线portal接\":700},\"1481560980000\":{\"有线1x接\":60,\"无线1x接\":80,\"有线portal接\":700,\"无线portal接\":2000},\"1481566140000\":{\"有线1x接\":100,\"无线1x接\":500,\"有线portal接\":320,\"无线portal接\":400},\"1481634360000\":{\"有线1x接\":220,\"无线1x接\":330,\"有线portal接\":300,\"无线portal接\":666},\"1481641440000\":{\"有线1x接\":200,\"无线1x接\":500,\"有线portal接\":770,\"无线portal接\":660},\"1481576220000\":{\"有线1x接\":360,\"无线1x接\":800,\"有线portal接\":290,\"无线portal接\":550},\"1481638380000\":{\"有线1x接\":600,\"无线1x接\":412,\"有线portal接\":260,\"无线portal接\":950}}}}";

//			String zlogresponse = "{\"onlineDetail\":{\"教师\":{\"etcOnlineTimePerDay\":0,\"etcOnlineTimePerRecord\":0,\"pcOnlineTimePerDay\":7.080268,\"pcOnlineTimePerRecord\":6.076185,\"wirelessOnlineTimePerDay\":23.575,\"wirelessOnlineTimePerRecord\":15.716666},\"学生\":{\"etcOnlineTimePerDay\":0,\"etcOnlineTimePerRecord\":0,\"pcOnlineTimePerDay\":7.080268,\"pcOnlineTimePerRecord\":6.076185,\"wirelessOnlineTimePerDay\":23.575,\"wirelessOnlineTimePerRecord\":15.716666}},\"flow\":{\"教师\":{\"etcFlowPerDay\":0,\"etcFlowPerRecord\":0,\"pcFlowPerDay\":0,\"pcFlowPerRecord\":0,\"wirelessFlowPerDay\":0,\"wirelessFlowPerRecord\":0},\"学生\":{\"etcFlowPerDay\":0,\"etcFlowPerRecord\":0,\"pcFlowPerDay\":0,\"pcFlowPerRecord\":0,\"wirelessFlowPerDay\":0,\"wirelessFlowPerRecord\":0}},\"accessTypeCount\":{\"学生\":{\"有线1x接\":1000,\"无线1x接\":2000,\"有线portal接\":300,\"无线portal接\":2000},\"教师\":{\"有线1x接\":10,\"无线1x接\":20,\"有线portal接\":30,\"无线portal接\":50}},\"accessTypeTrend\":{\"学生\":{\"1481676940000\":{\"有线1x接\":10,\"无线1x接\":20,\"有线portal接\":100,\"无线portal接\":50},\"1481582580000\":{\"有线1x接\":30,\"无线1x接\":50,\"有线portal接\":200,\"无线portal接\":80},\"1481560980000\":{\"有线1x接\":50,\"无线1x接\":200,\"有线portal接\":200,\"无线portal接\":80},\"1481566140000\":{\"有线1x接\":50,\"无线1x接\":30,\"有线portal接\":3000,\"无线portal接\":800},\"1481634360000\":{\"有线1x接\":60,\"无线1x接\":70,\"有线portal接\":200,\"无线portal接\":600},\"1481641440000\":{\"有线1x接\":100,\"无线1x接\":200,\"有线portal接\":400,\"无线portal接\":500},\"1481576220000\":{\"有线1x接\":30,\"无线1x接\":20,\"有线portal接\":550,\"无线portal接\":80},\"1481638380000\":{\"有线1x接\":0,\"无线1x接\":0,\"有线portal接\":300,\"无线portal接\":700}},\"教师\":{\"1481576940000\":{\"有线1x接\":100,\"无线1x接\":200,\"有线portal接\":300,\"无线portal接\":2000},\"1481582580000\":{\"有线1x接\":200,\"无线1x接\":400,\"有线portal接\":500,\"无线portal接\":700},\"1481560980000\":{\"有线1x接\":60,\"无线1x接\":80,\"有线portal接\":700,\"无线portal接\":2000},\"1481566140000\":{\"有线1x接\":100,\"无线1x接\":500,\"有线portal接\":320,\"无线portal接\":400},\"1481634360000\":{\"有线1x接\":220,\"无线1x接\":330,\"有线portal接\":300,\"无线portal接\":666},\"1481641440000\":{\"有线1x接\":200,\"无线1x接\":500,\"有线portal接\":770,\"无线portal接\":660},\"1481576220000\":{\"有线1x接\":360,\"无线1x接\":800,\"有线portal接\":290,\"无线portal接\":550},\"1481638380000\":{\"有线1x接\":600,\"无线1x接\":412,\"有线portal接\":260,\"无线portal接\":950}}}}";
			JSONObject zlogJsonObject=JSONObject.fromObject(zlogresponse);
			if(!zlogJsonObject.isNullObject()){
				JSONObject countObject = (JSONObject) zlogJsonObject.get("accessTypeCount");
				JSONObject studentObject = (JSONObject) countObject.get(type);
				if(!studentObject.isNullObject()){
					Iterator keys = studentObject.keys();
					Map<String, Long> map = new LinkedHashMap<String, Long>();
					while(keys.hasNext()){
						String key = keys.next().toString();		
						String value = studentObject.getString(key);
						map.put(key, Long.valueOf(value));
					}
					Map<String, Long> sortMap = sortMapByValue(map);
					Map<String, Long> finalMap = new LinkedHashMap<String, Long>();
					JSONObject jsonbject = new JSONObject();
					JSONObject json = new JSONObject();
					//接入类型超过4种，取前3种人数最多的接入类型，其他接入类型用“其他”表示，总数为这些接入的人数之和
					if(sortMap.size()<=4){
						finalMap = sortMap;
					}else{
						int i = 0;
						long otherCount = 0;
						for(String key:sortMap.keySet()){
							if(i<=2){
								finalMap.put(key,sortMap.get(key));
							}else{
								otherCount += sortMap.get(key);
							}
							i++;
						}
						finalMap.put("其他", otherCount);
					}
					json.putAll(finalMap);
					//计算各个接入类型所占百分比
					List<String> percentList = new ArrayList<String>();
					long totalCount = 0;
					for(String key:finalMap.keySet()){
						totalCount += finalMap.get(key);
					}
					BigDecimal totalDecimal = new BigDecimal(totalCount);
					BigDecimal excludeLastTotal = new BigDecimal(0);
					int index = 0;
					for(String key:finalMap.keySet()){
						if(totalCount==0){
							percentList.add("0");
						}else{
							if(index<finalMap.size()-1){
								BigDecimal unit = new BigDecimal(finalMap.get(key)).multiply(new BigDecimal(100));
								BigDecimal result = unit.divide(totalDecimal,2, RoundingMode.HALF_UP);
								excludeLastTotal = excludeLastTotal.add(result);
								percentList.add(result.toString());
							}else{
								BigDecimal last = new BigDecimal(100).subtract(excludeLastTotal).setScale(2);
								percentList.add(last.toString());
							}
						}
						index ++;
					}
					JSONArray ja = JSONArray.fromObject(percentList);
					jsonbject.put("percent", ja.toString());
					jsonbject.put("accessTypeCount", json.toString());
					
					//封装趋势图数据
					JSONObject trendObject = (JSONObject) zlogJsonObject.get("accessTypeTrend");
					JSONObject studentTrendObject = (JSONObject) trendObject.get(type);
					Iterator trendKeys = studentTrendObject.keys();
					Map<String, Map<String, Long>> trendDataMap = new TreeMap<String, Map<String, Long>>();
					while(trendKeys.hasNext()){
						//按时间排序
						String key = trendKeys.next().toString();
						JSONObject dataObject = (JSONObject) studentTrendObject.get(key);
						Iterator dataKeys = dataObject.keys();
						Map<String, Long> trendMap = new LinkedHashMap<String, Long>();
						Map<String, Long> realMap = new LinkedHashMap<String, Long>();
						while(dataKeys.hasNext()){
							String trendkey = dataKeys.next().toString();
							String value = dataObject.getString(trendkey);
							trendMap.put(trendkey, Long.valueOf(value));
						}
						long total = 0;
						for(String mapKey : trendMap.keySet()){
							if(!finalMap.containsKey(mapKey) && finalMap.containsKey("其他")){
								total += trendMap.get(mapKey);
								realMap.put("其他", total);
							}else if(finalMap.containsKey(mapKey)){
								realMap.put(mapKey, trendMap.get(mapKey));
							}
						}
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String time = sdf.format(new Date(Long.valueOf(key)));
						trendDataMap.put(time, realMap);
					}
					List<String> timeList = new ArrayList<String>();
					Map<String,List<Long>> typeMap = new TreeMap<String, List<Long>>();
					for(String key:trendDataMap.keySet()){
						timeList.add(key);
						Map<String, Long> map2 = trendDataMap.get(key);
						for(String typeKey: map2.keySet()){
							if(typeMap.containsKey(typeKey)){
								typeMap.get(typeKey).add(map2.get(typeKey));
							}else{
								List<Long> list = new ArrayList<Long>();
								list.add(map2.get(typeKey));
								typeMap.put(typeKey, list);
							}
						}
					}
					JSONArray timeArray = JSONArray.fromObject(timeList);
					jsonbject.put("timeArray",timeArray.toString());
					JSONObject jsob = new JSONObject();
					jsob.putAll(typeMap);
					jsonbject.put("trendData", jsob.toString());
					return jsonbject.toString();
				}else{
					return null;
				}
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ZNMSLogger.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * map按照value降序排序
	 * @param oriMap
	 * @return
	 */
    private Map<String, Long> sortMapByValue(Map<String, Long> oriMap){
        if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<String, Long> sortedMap = new LinkedHashMap<String, Long>();
		List<Map.Entry<String, Long>> entryList = new ArrayList<Map.Entry<String, Long>>(
				oriMap.entrySet());
		Collections.sort(entryList, new Comparator<Map.Entry<String,Long>>() {
           //降序排序
           public int compare(Entry<String, Long> o1,
                   Entry<String, Long> o2) {
               return o2.getValue().compareTo(o1.getValue());
           }
           
       });

		Iterator<Map.Entry<String, Long>> iter = entryList.iterator();
		Map.Entry<String, Long> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), Long.valueOf(tmpEntry.getValue()));
		}
		return sortedMap;
	}

	
}
