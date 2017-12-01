/**
 * 
 */
package info.zznet.znms.web.module.apInformation.service;

import java.util.List;

import info.zznet.znms.base.entity.ApRegion;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.BaseService;

/**
 * @author dell001
 *
 */
public interface ApRegionService extends BaseService{

	/**
	 * @return
	 */
	List<ApRegion> findAllApRegion();

	/**
	 * 
	 * @param apRegionName
	 * @return
	 */
	ApRegion checkRegionNameRepeat(String apRegionName);

	/**
	 * @param region
	 */
	void add(ApRegion region);

	/**
	 * @param pager
	 * @param apRegionName
	 * @return
	 */
	List<ApRegion> findRegion(Pager<ApRegion> pager, String apRegionName);

	/**
	 * @param apRegionName
	 * @return
	 */
	long getCount(String apRegionName);

	/**
	 * @param apRegionUuid
	 * @param isCascadeDelete
	 */
	void deleteRegion(String apRegionUuid, Integer isCascadeDelete);

	/**
	 * @param apRegionUuid
	 * @return
	 */
	ApRegion selectByPrimaryKey(String apRegionUuid);


}
