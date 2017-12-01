package info.zznet.znms.web.module.screen.service;


import java.util.List;

import info.zznet.znms.base.entity.FlowData;
import info.zznet.znms.web.module.common.service.BaseService;

public interface  FlowDataService  extends BaseService{
	
	List<FlowData> findAll();
	
	int addFlowData(String data);
	
	
}
