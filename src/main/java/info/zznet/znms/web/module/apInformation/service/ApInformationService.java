/**
 * 
 */
package info.zznet.znms.web.module.apInformation.service;

import info.zznet.znms.base.entity.ApInformation;
import info.zznet.znms.web.module.apInformation.bean.DeviceSearchBean;
import info.zznet.znms.web.module.common.service.BaseService;

import java.util.List;

/**
 * @author dell001
 *
 */
public interface ApInformationService extends BaseService{

	/**
	 * 
	 * @param bean
	 * @return
	 */
	List<ApInformation> findApByCondition(DeviceSearchBean bean);
	
	/**
	 * @param apInformationUuid
	 * @param apAxis
	 */
	void update(String[] apInformationUuids, String apAxis);

	/**
	 * 在所选区域中随机生成选中ap的位置
	 * @param apInformationUuid
	 * @param apRegionUuid
	 */
	void updateByRegion(String[] apInformationUuids, String apRegionUuid);

	/**
	 * @param apInformationUuid
	 * @return
	 */
	ApInformation selectByPrimaryKey(String apInformationUuid);

	ApInformation findApByApMac(String apMac);
	
	void updateByPrimaryKeySelective(ApInformation record);
	int addApInformation(ApInformation record);
	List<ApInformation> findAll();
	
	int deleteByPrimaryKey(String apInformationUuid);
	 
	String disperse(String axis);
	
	String generateRandomApaxis(String[] regionArray);

}
