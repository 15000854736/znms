package info.zznet.znms.base.dao;

import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.Screen;

import java.util.List;

@Repository("screenMapper")
public interface ScreenMapper extends BaseMapper{
    int deleteByPrimaryKey(String id);

    int insert(Screen record);

    int insertSelective(Screen record);

    Screen selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Screen record);

    int updateByPrimaryKey(Screen record);

    List<Screen> findAll();
    
    List<Screen> findByName(String name);
    
    List<Screen> findByCode(String code);
}