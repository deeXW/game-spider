package com.zdf.dao.dfwd;



import java.util.List;
import com.zdf.dao.base.BaseDao;
import com.zdf.entity.dfwd.EcmsNewsIndexEntity;
import com.zdf.dto.dfwd.QueryEcmsNewsIndexDto;


public interface EcmsNewsIndexDao extends BaseDao<Integer, EcmsNewsIndexEntity>{


	/**
	* 查询数据列表
	*/
	List<EcmsNewsIndexEntity> queryList(QueryEcmsNewsIndexDto queryEcmsNewsIndexDto);

}