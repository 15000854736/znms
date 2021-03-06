/**
 * 
 */
package info.zznet.znms.web.module.apInformation.service.impl;

import info.zznet.znms.base.dao.ApInformationMapper;
import info.zznet.znms.base.dao.ApRegionMapper;
import info.zznet.znms.base.entity.ApInformation;
import info.zznet.znms.base.entity.ApRegion;
import info.zznet.znms.base.job.ApInfomationJob;
import info.zznet.znms.web.module.apInformation.bean.DeviceSearchBean;
import info.zznet.znms.web.module.apInformation.service.ApInformationService;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dell001
 *
 */
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("apInformationService")
public class ApInformationServiceImpl extends BaseServiceImpl implements ApInformationService{

	@Autowired
	private ApInformationMapper apInformationMapper;
	
	@Autowired
	private ApRegionMapper apRegionMapper;

	@Override
	public List<ApInformation> findApByCondition(DeviceSearchBean bean) {
		String acIp = bean.getAcIp();
		String apName = bean.getApName();
		int apPositionStatus = bean.getApPositionStatus();
		String apRegionUuid = bean.getApRegionUuid();
		return apInformationMapper.findApByCondition(acIp, apName, apPositionStatus, apRegionUuid);
	}
	
	@Override
	public void update(String[] indexes, String apAxis) {
		boolean needDisperse = indexes.length > 1;
		for(String index : indexes) {
			String[] indexArr = StringUtils.split(index,",");
			if(null!=index&&indexArr.length==2){
				String _apAxis = needDisperse?disperse(apAxis):apAxis;
				apInformationMapper.updateApAxis(indexArr[0], _apAxis);
				ApInformation apInformation = ApInfomationJob.apInfomationMap.get(indexArr[1]);
				if(null!=apInformation){
					apInformation.setApAxis(_apAxis);
					ApInfomationJob.apInfomationMap.put(indexArr[1],apInformation);
				}
			}
		}
	}
	
	@Override
	public String disperse(String axis){
		BigDecimal x = new BigDecimal(axis.split(",")[0]);
		BigDecimal y = new BigDecimal(axis.split(",")[1]);
		x = x.add(new BigDecimal(new Random().nextFloat() * 5f));
		y = y.add(new BigDecimal(new Random().nextFloat() * 5f));
		return x.toString()+","+y.toString();
	}

	@Override
	public void updateByRegion(String[] indexes, String apRegionUuid) {
		ApRegion apRegion = apRegionMapper.selectByPrimaryKey(apRegionUuid);
		String[] regionArray = apRegion.getApRegionCoordinate().split(",");
		for(String index : indexes) {
			String[] indexArr = StringUtils.split(index,",");
			if(null!=index&&indexArr.length==2){
				String apAxis = generateRandomApaxis(regionArray);
				apInformationMapper.updateApAxisByRegion(indexArr[0], apAxis, apRegionUuid);
				ApInformation apInformation = ApInfomationJob.apInfomationMap.get(indexArr[1]);
				if(null!=apInformation){
					apInformation.setApAxis(apAxis);
					apInformation.setApRegionUuid(apRegionUuid);
					ApInfomationJob.apInfomationMap.put(indexArr[1],apInformation);
				}
			}
		}
	}
	
	/**
	 * 指定区域内生成ap坐标
	 * @param regionArray
	 * @param regionList
	 * @return
	 */
	@Override
	public String generateRandomApaxis(String[] regionArray){
		List<String> regionList = new ArrayList<String>();
		for(int a=0;a<regionArray.length;a++){
			regionList.add(regionArray[a]);
		}
		//从区域中随机取3个值
		String []randomRegion = new String[3];
		for(int i=0;i<3;i++){
			int regionIndex = new Random().nextInt(regionList.size());
			randomRegion[i] = regionList.get(regionIndex);
			regionList.remove(regionIndex);
		}
		
		//在区域中随机生成ap坐标
		double xParam = new Random().nextFloat()*1;
		double yParam = new Random().nextFloat()*1;
		if(xParam+yParam>1){
			xParam=1-xParam;
			yParam=1-yParam;
		}
		BigDecimal x = BigDecimal.valueOf(
				(1-xParam-yParam)*Double.valueOf(randomRegion[0].split("-")[0])+
				xParam*Double.valueOf(randomRegion[1].split("-")[0])+
				yParam*Double.valueOf(randomRegion[2].split("-")[0])
				);
		BigDecimal y = BigDecimal.valueOf(
				(1-xParam-yParam)*Double.valueOf(randomRegion[0].split("-")[1])+
				xParam*Double.valueOf(randomRegion[1].split("-")[1])+
				yParam*Double.valueOf(randomRegion[2].split("-")[1])
				);
		return x.toString()+","+y.toString();
	}

	@Override
	public ApInformation selectByPrimaryKey(String apInformationUuid) {
		return apInformationMapper.selectByPrimaryKey(apInformationUuid);
	}

	@Override
	public ApInformation findApByApMac(String apMac) {
		
		return apInformationMapper.findApByApMac(apMac);
	}

	@Override
	public void updateByPrimaryKeySelective(ApInformation record) {
		apInformationMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public int addApInformation(ApInformation record){
		return apInformationMapper.insertSelective(record);
	}

	@Override
	public List<ApInformation> findAll() {
		return apInformationMapper.findAll();
	}

	@Override
	public int deleteByPrimaryKey(String apInformationUuid) {
		return apInformationMapper.deleteByPrimaryKey(apInformationUuid);
	}

}
