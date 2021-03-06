package info.zznet.znms.base.dao;

import java.util.List;

import info.zznet.znms.base.entity.ApInformation;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("apInformationMapper")
public interface ApInformationMapper extends BaseMapper{
    int deleteByPrimaryKey(String apInformationUuid);

    int insert(ApInformation record);

    int insertSelective(ApInformation record);

    ApInformation selectByPrimaryKey(String apInformationUuid);

    int updateByPrimaryKeySelective(ApInformation record);

    int updateByPrimaryKey(ApInformation record);
    
    
	/**
	 * @param acIp
	 * @param apName
	 * @param apPositionStatus
	 * @return
	 */
	List<ApInformation> findApByCondition(@Param("acIp")String acIp, @Param("apName")String apName,
			@Param("apPositionStatus")int apPositionStatus, @Param("apRegionUuid")String apRegionUuid);
	
	/**
	 * @param apInformationUuid
	 * @param apAxis
	 */
	void updateApAxis(@Param("apInformationUuid")String apInformationUuid, @Param("apAxis")String apAxis);

	/**
	 * @param apInformationUuid
	 * @param apAxis
	 * @param apRegionUuid
	 */
	void updateApAxisByRegion(@Param("apInformationUuid")String apInformationUuid, @Param("apAxis")String apAxis,
			@Param("apRegionUuid")String apRegionUuid);


	ApInformation findApByApMac(@Param("apMac")String apMac);
	
	int updateByAPMac(ApInformation record);

	/**
	 * 获取所有
	 * @return
	 */
	List<ApInformation> findAll();
	
	/**
	 * 总和
	 * @return
	 */
	Long countAll();

	/**
	 * 清除区域下ap的坐标
	 * @param apRegionUuid
	 */
	void clearApAxisByApRegionUuid(@Param("apRegionUuid")String apRegionUuid);

	/**
	 * 清除区域下ap的apRegionUuid
	 * @param apRegionUuid
	 */
	void clearApRegionUuid(@Param("apRegionUuid")String apRegionUuid);

}