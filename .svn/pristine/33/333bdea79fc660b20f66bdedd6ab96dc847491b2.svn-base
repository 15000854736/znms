package info.zznet.znms.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.ApRegion;
import info.zznet.znms.web.module.common.page.Pager;

@Repository("apRegionMapper")
public interface ApRegionMapper extends BaseMapper{
    int deleteByPrimaryKey(String apRegionUuid);

    int insert(ApRegion record);

    int insertSelective(ApRegion record);

    ApRegion selectByPrimaryKey(String apRegionUuid);

    int updateByPrimaryKeySelective(ApRegion record);

    int updateByPrimaryKey(ApRegion record);

	/**
	 * @return
	 */
	List<ApRegion> findAllApRegion();

	/**
	 * @param apRegionName
	 * @return
	 */
	ApRegion findRegionByName(@Param("apRegionName")String apRegionName);

	/**
	 * @param pager
	 * @param apRegionName
	 * @return
	 */
	List<ApRegion> findRegion(@Param("pager")Pager<ApRegion> pager, @Param("apRegionName")String apRegionName);

	/**
	 * @param apRegionName
	 * @return
	 */
	long getCount(@Param("apRegionName")String apRegionName);

}