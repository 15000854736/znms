package info.zznet.znms.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.AuthResult;

@Repository("authResultMapper")
public interface AuthResultMapper extends BaseMapper{
    int deleteByPrimaryKey(String authResultUuid);

    int insert(AuthResult record);

    int insertSelective(AuthResult record);

    AuthResult selectByPrimaryKey(String authResultUuid);

    int updateByPrimaryKeySelective(AuthResult record);

    int updateByPrimaryKey(AuthResult record);
    
    List<AuthResult> findAll();
    
}