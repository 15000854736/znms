package info.zznet.znms.base.dao;

import info.zznet.znms.base.entity.MemoryTemplate;
import info.zznet.znms.base.entity.WirelessStatisticalConfiguration;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("wirelessStatisticalConfigurationMapper")
public interface WirelessStatisticalConfigurationMapper extends BaseMapper{
    int deleteByPrimaryKey(String wirelessStatisticalConfigurationUuid);

    int insert(WirelessStatisticalConfiguration record);

    int insertSelective(WirelessStatisticalConfiguration record);

    WirelessStatisticalConfiguration selectByPrimaryKey(String wirelessStatisticalConfigurationUuid);

    int updateByPrimaryKeySelective(WirelessStatisticalConfiguration record);

    int updateByPrimaryKey(WirelessStatisticalConfiguration record);

	/**
	 * @return
	 */
	List<WirelessStatisticalConfiguration> findAll();

	/**
	 * @param hsotUuid
	 * @return
	 */
	WirelessStatisticalConfiguration findByHostUuid(@Param("hostUuid")String hsotUuid);

	/**
	 * @param hostUuid
	 * @param apTemplateUuid
	 * @param userTemplateUuid
	 * @return
	 */
	WirelessStatisticalConfiguration checkWirelessTemplateRepeat(@Param("hostUuid")String hostUuid, 
			@Param("apTemplateUuid")String apTemplateUuid,
			@Param("userTemplateUuid")String userTemplateUuid);

	/**
	 * 根据hostUuid删除无线统计配置
	 * @param hostUuid
	 */
	void deleteByHostUuid(String hostUuid);

}