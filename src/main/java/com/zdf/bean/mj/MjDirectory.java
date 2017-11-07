package com.zdf.bean.mj;

import java.util.Date;

public class MjDirectory {
	/**
	 * id
	 */
	private Long   id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 纯粹标题
	 */
	private String pureTitle;
	
	/**
	 * 内容描述
	 */
	private String contentDesc;
	
	/**
	 * 类型(7大类,BBC,资讯)
	 */
	private String type;
	
	/**
	 * 影片类型 目前三类：meiju(美剧)，dianying(电影)，hanju(韩剧)
	 */
	private String filmType;
	
	/**
	 * 类型描述
	 */
	private String typeDesc;
	
	/**
	 * 播出方
	 */
	private String broadcastFrom;
	
	/**
	 * 地区描述
	 */
	private String area;
	
	/**
	 * 主演
	 */
	private String starring;
	
	/**
	 * 语言
	 */
	private String language;
	
	/**
	 * 图片地址
	 */
	private String imgUrl;
	
	/**
	 * 首播时间
	 */
	private String premiereTime;
	
	/**
	 * 英文名
	 */
	private String englishName;
	
	/**
	 * 别名
	 */
	private String alias;
	
	/**
	 * 播出时间
	 */
	private String broadcastTime;
	
	/**
	 * 集数
	 */
	private String totalCount;
	
	/**
	 * 是否更新中 0 否； 1 是
	 */
	private String isUpdate;
	
	/**
	 * 更新的集数据
	 */
	private String updateCount;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 下季情况
	 */
	private String nextSeasonSituation;
	
	/**
	 * 排序
	 */
	private String sort;
	
	/**
	 * 下载次数
	 */
	private Long downloadCount;

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

	
	
	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPureTitle() {
		return pureTitle;
	}

	public void setPureTitle(String pureTitle) {
		this.pureTitle = pureTitle;
	}

	public String getContentDesc() {
		return contentDesc;
	}

	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}

	public String getFilmType() {
		return filmType;
	}

	public void setFilmType(String filmType) {
		this.filmType = filmType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getBroadcastFrom() {
		return broadcastFrom;
	}

	public void setBroadcastFrom(String broadcastFrom) {
		this.broadcastFrom = broadcastFrom;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStarring() {
		return starring;
	}

	public void setStarring(String starring) {
		this.starring = starring;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getPremiereTime() {
		return premiereTime;
	}

	public void setPremiereTime(String premiereTime) {
		this.premiereTime = premiereTime;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getBroadcastTime() {
		return broadcastTime;
	}

	public void setBroadcastTime(String broadcastTime) {
		this.broadcastTime = broadcastTime;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNextSeasonSituation() {
		return nextSeasonSituation;
	}

	public void setNextSeasonSituation(String nextSeasonSituation) {
		this.nextSeasonSituation = nextSeasonSituation;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
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

	public Long getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Long downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(String updateCount) {
		this.updateCount = updateCount;
	}

	@Override
	public String toString() {
		return "MjDirectory [id=" + id + ", title=" + title + ", pureTitle="
				+ pureTitle + ", contentDesc=" + contentDesc + ", type=" + type
				+ ", filmType=" + filmType + ", typeDesc=" + typeDesc
				+ ", broadcastFrom=" + broadcastFrom + ", area=" + area
				+ ", starring=" + starring + ", language=" + language
				+ ", imgUrl=" + imgUrl + ", premiereTime=" + premiereTime
				+ ", englishName=" + englishName + ", alias=" + alias
				+ ", broadcastTime=" + broadcastTime + ", totalCount="
				+ totalCount + ", isUpdate=" + isUpdate + ", updateCount="
				+ updateCount + ", remark=" + remark + ", nextSeasonSituation="
				+ nextSeasonSituation + ", sort=" + sort + ", downloadCount="
				+ downloadCount + ", createTime=" + createTime + ", createBy="
				+ createBy + ", changeTime=" + changeTime + ", changeBy="
				+ changeBy + "]";
	}
}
