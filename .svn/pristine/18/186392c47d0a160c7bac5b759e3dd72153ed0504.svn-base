/**
 * 
 */
package info.zznet.znms.web.module.common.constants;

import info.zznet.znms.web.module.systemLog.collector.Facility;
import info.zznet.znms.web.module.systemLog.collector.Severity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dell001
 *日志级别类
 */
public class SystemLogPriority {
	
	public static Map<Integer,String> generatePriorityMap(){
		Map<Integer,String> map = new HashMap<Integer, String>();
		for(Severity severity :Severity.values()){
			map.put(severity.getCode(), severity.getText());
		}
		return map;
	}
	
	public static String findValueByKey(Integer key){
		Map<Integer,String> map = generatePriorityMap();
		for(Integer priorityId : map.keySet()){
			if(key.intValue()==priorityId.intValue()){
				return map.get(key);
			}
		}
		return null;
	}

}
