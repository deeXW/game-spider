package com.zdf.dto.dfwd;

import com.zdf.dto.base.AbstractPageParamDto;
import java.util.Date;
/**
 * 
 * @ClassName: QueryEcmsNewsData1Dto
 * @Description:
 * @author: zhongdifeng
 *
 */
public class QueryEcmsNewsData1Dto extends AbstractPageParamDto {


    /**
    * id
    */
    private Integer  id;


    /**
    * classid
    */
    private Integer  classid;


    /**
    * keyid
    */
    private String keyid;


    /**
    * dokey
    */
    private Integer  dokey;


    /**
    * newstempid
    */
    private Integer  newstempid;


    /**
    * closepl
    */
    private Integer  closepl;


    /**
    * haveaddfen
    */
    private Integer  haveaddfen;


    /**
    * infotags
    */
    private String infotags;


    /**
    * writer
    */
    private String writer;


    /**
    * befrom
    */
    private String befrom;


    /**
    * newstext
    */
    private String newstext;

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
	public String getKeyid() {
		return keyid;
	}
	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}
	public Integer getDokey() {
		return dokey;
	}
	public void setDokey(Integer dokey) {
		this.dokey = dokey;
	}
	public Integer getNewstempid() {
		return newstempid;
	}
	public void setNewstempid(Integer newstempid) {
		this.newstempid = newstempid;
	}
	public Integer getClosepl() {
		return closepl;
	}
	public void setClosepl(Integer closepl) {
		this.closepl = closepl;
	}
	public Integer getHaveaddfen() {
		return haveaddfen;
	}
	public void setHaveaddfen(Integer haveaddfen) {
		this.haveaddfen = haveaddfen;
	}
	public String getInfotags() {
		return infotags;
	}
	public void setInfotags(String infotags) {
		this.infotags = infotags;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getBefrom() {
		return befrom;
	}
	public void setBefrom(String befrom) {
		this.befrom = befrom;
	}
	public String getNewstext() {
		return newstext;
	}
	public void setNewstext(String newstext) {
		this.newstext = newstext;
	}
}
