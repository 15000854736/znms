package info.zznet.znms.base.util;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.web.WebRuntimeData;
import net.sf.json.JSONObject;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaUtil {

	public static void sendMessage(JSONObject json, String topic) {
		KafkaProducer<String, String> producer = WebRuntimeData.instance.getProducer();
		if(producer != null) {
			try {
				producer.send(new ProducerRecord<String, String>(topic, json.toString()));
			} catch(Exception e) {
				ZNMSLogger.debug(e.getMessage());
			}
		}
	}
}
