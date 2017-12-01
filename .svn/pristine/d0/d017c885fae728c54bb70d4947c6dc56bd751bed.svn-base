package info.zznet.znms.base.dao;

import info.zznet.znms.base.entity.SmAdmin;
import info.zznet.znms.base.entity.SmRole;
import info.zznet.znms.web.module.common.page.PageBounds;
import info.zznet.znms.web.module.system.bean.SmAdminBean;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SmAdminMapper<T1,T2> extends BaseMapper{
    int deleteByPrimaryKey(String adminUuid);

    int insert(SmAdmin record);

    int insertSelective(SmAdmin record);

    SmAdmin findByPrimaryKey(String adminUuid);

    int updateByPrimaryKeySelective(SmAdmin record);

    int updateByPrimaryKey(SmAdmin record);
    
    public List<SmRole> findRoleList();
	
	/**
	 * 根据用户id查找用户
	 * @param userName 管理员用户名
	 * @return
	 */
	List<SmAdmin> findByAdminId(@Param("adminId")String adminId);
	
	void deleteByUuidList(List<String> uuidList);
	
	List<SmAdmin> findAdminInfos(PageBounds bounds, SmAdminBean smAdminBean);
    
	int count(T2 t);
}