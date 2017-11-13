package com.zdf.dao.dfwd;



import java.util.List;
import com.zdf.dao.base.BaseDao;
import com.zdf.entity.dfwd.EcmsNewsData1Entity;
import com.zdf.dto.dfwd.QueryEcmsNewsData1Dto;


public interface EcmsNewsData1Dao extends BaseDao<Integer, EcmsNewsData1Entity>{


	/**
	* 查询数据列表
	*/
	List<EcmsNewsData1Entity> queryList(QueryEcmsNewsData1Dto queryEcmsNewsData1Dto);

}