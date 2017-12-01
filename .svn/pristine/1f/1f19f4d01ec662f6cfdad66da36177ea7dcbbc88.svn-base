package info.zznet.znms.web.module.screen.service;

import info.zznet.znms.base.entity.Screen;
import info.zznet.znms.web.module.common.service.BaseService;

import java.util.List;

/**
 * Created by shenqilei on 2016/11/29.
 */
public interface ScreenService extends BaseService {
    List<Screen> findAll();
    
    Screen findById(String id);
    
    int addScreen(Screen screen);
    
    int deleteById(String id);
    
    int updateByIdSelective(Screen screen);
    
    int updateScreen(Screen screen);
    
    List<Screen> findByName(String name);
    
    List<Screen> findByCode(String code);

	/**
	 * @param type
	 * @return
	 */
	String findStudentTeacherAccessTypeData(String type);

    
}
