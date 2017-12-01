package info.zznet.znms.base.job;

import info.zznet.znms.base.entity.ApInformation;
import info.zznet.znms.base.util.DateUtil;
import info.zznet.znms.web.module.apInformation.service.ApInformationService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



@Service
@DisallowConcurrentExecution
public class DeleteAPJob {
	
	@Autowired
	private ApInformationService apInformationService;
	
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteAp(){
		
		try {
			List<ApInformation>  apInformations=apInformationService.findAll();
			for (ApInformation apInformation : apInformations) {
				int num=DateUtil.daysBetween(apInformation.getLastUpdateTime());
				if(num>=15){
					apInformationService.deleteByPrimaryKey(apInformation.getApInformationUuid());
				}
			}
		} catch (ParseException e) {
		}
	}
}
