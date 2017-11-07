package com.zdf.bean.mj;

import java.io.Serializable;
import java.util.Date;

public class MjDirectoryLinks implements Serializable{

	private static final long serialVersionUID = 1167434172115922206L;

	/**
	 * id
	 */
	private Long   id;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 目录id
	 */
	private Long directoryId;
	
	/**
	 * 链接名称
	 */
	private String downloadName;
	
	/**
	 * 下载链接
	 */
	private String downloadLink;
	
	/**
	 * 链接名称
	 */
	private String downloadName720;
	
	/**
	 * 下载链接720
	 */
	private String downloadLink720;
	
	/**
	 * 是否区分链接 1是 0否
	 */
	private String isDistinguish;
	
	/**
	 * 是否有字幕(1有 0无)
	 */
	private String isSubtitle;
	
	/**
	 * 分辨率[(有字幕 11:1024; 12:720),(无字幕 01:1080; 02:720; )]
	 */
	private String pixel;
	
	/**
	 * createTime
	 */
	private Date createTime;
	
	/**
	 * createBy
	 */
	private String createBy;
	
	/**
	 * changeTime
	 */
	private Date changeTime;
	
	/**
	 * changeBy
	 */
	private String changeBy;

	
	
	public String getDownloadName720() {
		return downloadName720;
	}

	public void setDownloadName720(String downloadName720) {
		this.downloadName720 = downloadName720;
	}

	public String getDownloadLink720() {
		return downloadLink720;
	}

	public void setDownloadLink720(String downloadLink720) {
		this.downloadLink720 = downloadLink720;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIsDistinguish() {
		return isDistinguish;
	}

	public void setIsDistinguish(String isDistinguish) {
		this.isDistinguish = isDistinguish;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDirectoryId() {
		return directoryId;
	}

	public void setDirectoryId(Long directoryId) {
		this.directoryId = directoryId;
	}

	public String getDownloadName() {
		return downloadName;
	}

	public void setDownloadName(String downloadName) {
		this.downloadName = downloadName;
	}

	public String getDownloadLink() {
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}

	public String getIsSubtitle() {
		return isSubtitle;
	}

	public void setIsSubtitle(String isSubtitle) {
		this.isSubtitle = isSubtitle;
	}

	public String getPixel() {
		return pixel;
	}

	public void setPixel(String pixel) {
		this.pixel = pixel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public String getChangeBy() {
		return changeBy;
	}

	public void setChangeBy(String changeBy) {
		this.changeBy = changeBy;
	}

	@Override
	public String toString() {
		return "MjDirectoryLinks [id=" + id + ", sort=" + sort
				+ ", directoryId=" + directoryId + ", downloadName="
				+ downloadName + ", downloadLink=" + downloadLink
				+ ", downloadName720=" + downloadName720 + ", downloadLink720="
				+ downloadLink720 + ", isDistinguish=" + isDistinguish
				+ ", isSubtitle=" + isSubtitle + ", pixel=" + pixel
				+ ", createTime=" + createTime + ", createBy=" + createBy
				+ ", changeTime=" + changeTime + ", changeBy=" + changeBy + "]";
	}

}