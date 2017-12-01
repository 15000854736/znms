package info.zznet.znms.base.dao;

import info.zznet.znms.base.entity.RrdDataInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("rrdDataInfoMapper")
public interface RrdDataInfoMapper extends BaseMapper {
    int deleteByPrimaryKey(@Param("rrdDataId")String rrdDataId);

    RrdDataInfo findById(@Param("id")String id);
    int insert(RrdDataInfo record);
    
    List<RrdDataInfo> findByTemplate(@Param("templateName")String templateName);
}