package info.zznet.znms.base.job;

import java.util.List;

import info.zznet.znms.base.dao.AcOidConfigMapper;
import info.zznet.znms.base.dao.HostMapper;
import info.zznet.znms.base.entity.AcOidConfig;
import info.zznet.znms.web.WebRuntimeData;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@DisallowConcurrentExecution
public class WebRuntimeDataRefreshJob {
	
	@Autowired
	private HostMapper hostMapper;
	
	@Autowired
	private AcOidConfigMapper acOidConfigMapper;
	
	@Scheduled(fixedDelay=60000)
	public void refreshBadStatusHostCount(){
		WebRuntimeData.instance.setBadStatusHostCount(hostMapper.getHostCountByWorkStatus(0));
	}
	
	@Scheduled(cron = "0 0 1 * * ?")
	public void refreshAcOidConfigMap(){
		List<AcOidConfig> acOidConfigList= acOidConfigMapper.findAll();
		if(acOidConfigList != null){
			for(AcOidConfig acOidConfig : acOidConfigList) {
				WebRuntimeData.instance.saveAcOidConfig(acOidConfig);
			}
		}		
	}
}
