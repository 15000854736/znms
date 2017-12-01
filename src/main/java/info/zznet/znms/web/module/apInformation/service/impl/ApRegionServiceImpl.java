/**
 * 
 */
package info.zznet.znms.web.module.apInformation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import info.zznet.znms.base.dao.ApInformationMapper;
import info.zznet.znms.base.dao.ApRegionMapper;
import info.zznet.znms.base.dao.HostMapper;
import info.zznet.znms.base.entity.ApRegion;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.module.apInformation.service.ApRegionService;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;

/**
 * @author dell001
 *
 */
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("apRegionService")
public class ApRegionServiceImpl extends BaseServiceImpl implements ApRegionService{

	@Autowired
	private ApRegionMapper apRegionMapper;
	
	@Autowired
	private ApInformationMapper apInformationMapper;

	@Autowired
	private HostMapper hostMapper;
	
	@Override
	public List<ApRegion> findAllApRegion() {
		return apRegionMapper.findAllApRegion();
	}

	@Override
	public ApRegion checkRegionNameRepeat(String apRegionName) {
		return apRegionMapper.findRegionByName(apRegionName);
	}

	@Override
	public void add(ApRegion region) {
		region.setApRegionUuid(UUIDGenerator.getGUID());
		apRegionMapper.insert(region);
	}

	@Override
	public List<ApRegion> findRegion(Pager<ApRegion> pager, String apRegionName) {
		return apRegionMapper.findRegion(pager, apRegionName);
	}

	@Override
	public long getCount(String apRegionName) {
		return apRegionMapper.getCount(apRegionName);
	}

	@Override
	public void deleteRegion(String apRegionUuid, Integer isCascadeDelete) {
		if(isCascadeDelete.intValue()==1){
			//级联清除区域下ap的坐标
			apInformationMapper.clearApAxisByApRegionUuid(apRegionUuid);
			hostMapper.clearApAxisByApRegionUuid(apRegionUuid);
		}
		//清除区域下的关联的所有ap和主机的apRegionUuid
		apInformationMapper.clearApRegionUuid(apRegionUuid);
		hostMapper.clearApRegionUuid(apRegionUuid);
		apRegionMapper.deleteByPrimaryKey(apRegionUuid);
	}

	@Override
	public ApRegion selectByPrimaryKey(String apRegionUuid) {
		return apRegionMapper.selectByPrimaryKey(apRegionUuid);
	}

}
