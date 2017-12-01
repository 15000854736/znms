package info.zznet.znms.base.dao;

import info.zznet.znms.base.entity.ImportResult;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("importResultMapper")
public interface ImportResultMapper extends BaseMapper{
    int deleteByPrimaryKey(String id);

    int insert(ImportResult record);

    int insertSelective(ImportResult record);

    ImportResult selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ImportResult record);

    int updateByPrimaryKey(ImportResult record);
    
    List<ImportResult> findAll();
}