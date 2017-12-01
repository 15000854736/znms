package info.zznet.znms.base.job;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.web.util.ConfigUtil;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by shenqilei on 2016/9/21.
 */
@Service
@DisallowConcurrentExecution
public class SystemJob {


    //@Scheduled(initialDelay = 60*1000,fixedDelay = 3*1000)
    public void checkSystemAlive(){
        //TODO 系统保活

    }


    /**
     * 同步配置文件
     */
    @Scheduled(initialDelay = 60*1000,fixedDelay = 60*1000)
    public void syncConfig(){
        ConfigUtil.loadConfigure();
    }

}
