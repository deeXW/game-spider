package com.zdf.dto.dfwd;

import com.zdf.dto.base.AbstractPageParamDto;
import java.util.Date;
/**
 * 
 * @ClassName: QueryEcmsNewsIndexDto
 * @Description:
 * @author: zhongdifeng
 *
 */
public class QueryEcmsNewsIndexDto extends AbstractPageParamDto {


    /**
    * id
    */
    private Integer  id;


    /**
    * classid
    */
    private Integer  classid;


    /**
    * checked
    */
    private Integer  checked;


    /**
    * newstime
    */
    private Integer  newstime;


    /**
    * truetime
    */
    private Integer  truetime;


    /**
    * lastdotime
    */
    private Integer  lastdotime;


    /**
    * havehtml
    */
    private Integer  havehtml;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getClassid() {
		return classid;
	}
	public void setClassid(Integer classid) {
		this.classid = classid;
	}
	public Integer getChecked() {
		return checked;
	}
	public void setChecked(Integer checked) {
		this.checked = checked;
	}
	public Integer getNewstime() {
		return newstime;
	}
	public void setNewstime(Integer newstime) {
		this.newstime = newstime;
	}
	public Integer getTruetime() {
		return truetime;
	}
	public void setTruetime(Integer truetime) {
		this.truetime = truetime;
	}
	public Integer getLastdotime() {
		return lastdotime;
	}
	public void setLastdotime(Integer lastdotime) {
		this.lastdotime = lastdotime;
	}
	public Integer getHavehtml() {
		return havehtml;
	}
	public void setHavehtml(Integer havehtml) {
		this.havehtml = havehtml;
	}
}
