/**
 * 
 */
package info.zznet.znms.web.start;

import info.zznet.znms.base.dao.ThresholdValueMapper;
import info.zznet.znms.base.entity.ThresholdValue;
import info.zznet.znms.base.util.SpringContextUtil;
import info.zznet.znms.spider.Engine;
import info.zznet.znms.spider.bean.ScanResult;
import info.zznet.znms.web.WebRuntimeData;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dell001
 *
 */
public class ThresholdValueThread extends Thread{
	public static Map<String, List<ThresholdValue>> map = new ConcurrentHashMap<String, List<ThresholdValue>>();

	public void run(){
		Engine engine = (Engine) SpringContextUtil.getBean("engine");
		ThresholdValueMapper thresholdValueMapper = (ThresholdValueMapper) SpringContextUtil.getBean("thresholdValueMapper");
		updateGraphThresholdValueMap(thresholdValueMapper);
		while (true) {
			ScanResult result = null;
			try {
				result = engine.otherQueue.take();
				//检查是否触发相关阀值
				if(!WebRuntimeData.instance.getSystemOptionBean().isDisableAllThresholdValue()){
					engine.checkThresholdValueAndOperation(result, map);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
	
	public static void updateGraphThresholdValueMap(ThresholdValueMapper thresholdValueMapper){
		List<ThresholdValue> list = thresholdValueMapper.findAll();
		Map<String,List<ThresholdValue>> existsThresholdMap = new HashMap<>();

		for (ThresholdValue thresholdValue : list) {
			if(thresholdValue.getStatus()==1){
				//启用
				String key = thresholdValue.getGraphUuid();

				List<ThresholdValue> existsThresholdList = existsThresholdMap.get(key);
				if(null==existsThresholdList){
					existsThresholdList = new ArrayList<>();
					existsThresholdMap.put(key,existsThresholdList);
				}
				existsThresholdList.add(thresholdValue);

				List<ThresholdValue> valueList = map.get(key);
				if(null==valueList){
					valueList = new ArrayList<ThresholdValue>();
					map.put(key,valueList);
				}
				valueList.add(thresholdValue);
			}
		}

		Iterator<Map.Entry<String,List<ThresholdValue>>> it = map.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry<String,List<ThresholdValue>> entry = it.next();
			List<ThresholdValue> currentList = entry.getValue();
			if (!existsThresholdMap.containsKey(entry.getKey())){
				it.remove();
			}else{
				List<ThresholdValue> existsThresholdList = existsThresholdMap.get(entry.getKey());
				currentList.retainAll(existsThresholdList);
			}
		}

	}
}
