/**
 * 
 */
package info.zznet.znms.web.module.common.constants;

import info.zznet.znms.web.module.systemLog.collector.Facility;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dell001
 * 日志类型类
 *
 */
public class SystemLogFacility {
	
	public static Map<Integer,String> generateFacilityMap(){
		Map<Integer,String> map = new HashMap<Integer, String>();
		for(Facility facility :Facility.values()){
			map.put(facility.getCode(), facility.name());
		}
		return map;
	}
	
	public static String findValueByKey(Integer key){
		Map<Integer,String> map = generateFacilityMap();
		for(Integer facilityId: map.keySet()){
			if(key.intValue()==facilityId.intValue()){
				return map.get(key);
			}
		}
		return null;
	}

}
