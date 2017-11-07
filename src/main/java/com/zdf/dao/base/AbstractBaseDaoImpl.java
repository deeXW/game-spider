package com.zdf.dao.base;
import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

/** 
 *  
 *  
 * @param <T> 
 */  
public abstract class AbstractBaseDaoImpl<T>{  
  
    // SPRING JDBC模板接口  
    @Resource
    private JdbcTemplate jdbcTemplate;  
  
    public JdbcTemplate getJdbcTemplate() {  
        return jdbcTemplate;  
    }  
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    }  
  
}  