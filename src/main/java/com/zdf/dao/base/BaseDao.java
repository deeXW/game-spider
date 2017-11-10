package com.zdf.dao.base;

import java.io.Serializable;

/**
 * Created by zhongdifeng on 2017/8/23.
 */
public interface BaseDao<PK, T extends Serializable> {

    /**
     * 根据id获得对象
     */
    T getById(PK id);


    /**
     * 保存对象
     */
    PK insert(T entity);

    /**
     * 更新对象
     */
    int update(T entity);

    /**
     * 根据主键id删除对象
     */
    int delById(PK id);

    /**
     * 根据主键id逻辑删除对象
     * @param id
     * @return
     */
    int logicDelById(PK id);

}
