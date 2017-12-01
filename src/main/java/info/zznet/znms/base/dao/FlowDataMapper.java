package info.zznet.znms.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.FlowData;

@Repository("flowDataMapper")
public interface FlowDataMapper extends BaseMapper{
    int deleteByPrimaryKey(String id);

    int insert(FlowData record);

    int insertSelective(FlowData record);

    FlowData selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FlowData record);

    int updateByPrimaryKey(FlowData record);
    
    List<FlowData> findAll();
}