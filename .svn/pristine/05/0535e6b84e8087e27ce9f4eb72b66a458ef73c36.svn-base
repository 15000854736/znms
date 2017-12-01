package info.zznet.znms.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.sf.json.JSONArray;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.web.module.common.page.Pager;

@Repository("hostMapper")
public interface HostMapper extends BaseMapper{
    int deleteByPrimaryKey(String id);

    int insert(Host record);

    int insertSelective(Host record);

    Host selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Host record);

    int updateByPrimaryKey(Host record);

	/**
	 * 分页查询主机
	 * @param pager
	 * @param searchCondition
	 * @return
	 */
	List<Host> findPageList(@Param("pager")Pager<Host> pager, @Param("condition")JSONArray searchCondition);

	/**
	 * @param searchCondition
	 * @return
	 */
	Long getCount(@Param("condition")JSONArray searchCondition);

	/**
	 * 删除多个主机
	 * @param uuids
	 */
	void deleteHostByUuidList(@Param("uuids")List<String> uuids);

	/**
	 * @param ids
	 */
	void updateHostEnable(@Param("uuidList")List<String> uuidList);

	/**
	 * @param uuidList
	 */
	void updateHostDisable(@Param("uuidList")List<String> uuidList);

	/**
	 * 根据上层节点id查找该节点下可用的主机
	 * @param nodeUuid
	 * @return
	 */
	List<Host> findApplicableHost(@Param("nodeUuid")String nodeUuid);

	/**
	 * @param deviceUuid
	 * @return
	 */
	List<Host> findDeviceHost(@Param("deviceUuid")String deviceUuid);

	/**
	 * @return
	 */
	List<Host> findAll();

	/**
	 * @param hostIp
	 * @param id
	 * @return
	 */
	Host checkHostIp(@Param("hostIp")String hostIp, @Param("id")String id);

	/**
	 * @param hostName
	 * @param id
	 * @return
	 */
	Host checkHostName(@Param("hostName")String hostName, @Param("id")String id);

	/**
	 * 根据主机ip更改主机工作状态
	 * @param ip
	 * @param isReachable
	 */
	void updateHostWorkStatusByIP(@Param("hostIp")String ip, @Param("hostWorkStatus")int hostWorkStatus);

	/**
	 * 查找所有接入为无线控制器的主机
	 * @return
	 */
	List<Host> findAllAc();

	/**
	 * @param deviceTypeArray
	 * @param hostWorkStatus
	 * @return
	 */
	int getDeviceCountByCondition(@Param("deviceTypeArray")int[] deviceTypeArray, 
			@Param("hostWorkStatus")int hostWorkStatus);

	/**
	 * @param defaultAccessArray
	 * @return
	 */
	List<Host> getMainDeviceMonitorData(@Param("deviceTypeArray")int[] defaultAccessArray);

	/**
	 * 获取指定工作状态的主机数
	 * @return
	 */
	long getHostCountByWorkStatus(@Param("workStatus")Integer workStatus);
	
	List<Host> findShutDownHost(@Param("hostWorkStatus")Integer hostWorkStatus);

	/**
	 * @param hostIp
	 * @param hostName
	 * @param hostType
	 * @param apRegionUuid
	 * @return
	 */
	List findHostByCondition(@Param("hostIp")String hostIp, @Param("hostName")String hostName,  @Param("type")int hostType,
			 @Param("apPositionStatus")int apPositionStatus, @Param("apRegionUuid")String apRegionUuid);

	/**
	 * 更新主机坐标
	 * @param hostUuid
	 * @param hostAxis
	 */
	void updateApAxis(@Param("hostUuid")String hostUuid, @Param("hostAxis")String hostAxis);

	/**
	 * @param id
	 * @param hostAxis
	 * @param apRegionUuid
	 */
	void updateApAxisByRegion(@Param("id")String id, @Param("hostAxis")String hostAxis, @Param("apRegionUuid")String apRegionUuid);

	/**
	 * @param apRegionUuid
	 */
	void clearApRegionUuid(@Param("apRegionUuid")String apRegionUuid);

	/**
	 * @param apRegionUuid
	 */
	void clearApAxisByApRegionUuid(@Param("apRegionUuid")String apRegionUuid);
}