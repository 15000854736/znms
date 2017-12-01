package info.zznet.znms.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.AcOidConfig;

@Repository("acOidConfigMapper")
public interface AcOidConfigMapper extends BaseMapper{
	
	AcOidConfig findByCode(String acCode);
	
	List<AcOidConfig> findAll();
	
    int insert(AcOidConfig record);

    int insertSelective(AcOidConfig record);
    
}