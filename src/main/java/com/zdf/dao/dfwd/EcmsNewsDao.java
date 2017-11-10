package com.zdf.dao.dfwd;



import com.zdf.dao.base.BaseDao;
import com.zdf.dto.QueryEcmsNewsDTO;
import com.zdf.entity.EcmsNewsEntity;

import java.util.List;


public interface EcmsNewsDao extends BaseDao<Integer, EcmsNewsEntity> {


	/**
	* 查询数据列表
	*/
	List<EcmsNewsEntity> queryList(QueryEcmsNewsDTO queryEcmsNewsDTO);

}