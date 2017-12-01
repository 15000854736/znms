package info.zznet.znms.base.dao;

import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.ScreenConfig;

@Repository("screenConfigMapper")
public interface ScreenConfigMapper extends BaseMapper{
	
    int deleteByPrimaryKey(String screenId);

    int insert(ScreenConfig record);

    int insertSelective(ScreenConfig record);

    ScreenConfig selectByPrimaryKey(String screenId);

    int updateByPrimaryKeySelective(ScreenConfig record);

    int updateByPrimaryKey(ScreenConfig record);
}