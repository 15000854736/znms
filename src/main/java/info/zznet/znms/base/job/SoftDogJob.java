package info.zznet.znms.base.job;

import info.zznet.znms.softdog.DogDecryptAuthData;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@DisallowConcurrentExecution
public class SoftDogJob {
	
	@Scheduled(cron ="0 */60 * * * ?")
	public void updateApInfomation(){
		DogDecryptAuthData dog=new DogDecryptAuthData();
		//dog.run();
	}
}
