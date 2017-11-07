package com.zdf.bean.mj;

import java.util.Date;

public class MjDirectoryVideo {

	private static final long serialVersionUID = 1151542125620814723L;

	private Long id;

    private Long sort;

    /**
     * 0: 美剧汇资源
     */
    private String type;

    /**
     * 原来连接地址 通过此地址去获取视频连接
     */
    private String orgUrl;

    /**
     * 视频链接
     */
    private String videoUrl;

    private Long directoryId;

    /**
     * 视频名称
     */
    private String videoName;
    
    private Date createTime;

    private String createBy;

    private Date changeTime;

    private String changeBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getOrgUrl() {
        return orgUrl;
    }

    public void setOrgUrl(String orgUrl) {
        this.orgUrl = orgUrl == null ? null : orgUrl.trim();
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    public Long getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(Long directoryId) {
        this.directoryId = directoryId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName == null ? null : videoName.trim();
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
        this.createBy = createBy == null ? null : createBy.trim();
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
        this.changeBy = changeBy == null ? null : changeBy.trim();
    }

	@Override
	public String toString() {
		return "MjDirectoryVideo [id=" + id + ", sort=" + sort + ", type="
				+ type + ", orgUrl=" + orgUrl + ", videoUrl=" + videoUrl
				+ ", directoryId=" + directoryId + ", videoName=" + videoName
				+ ", createTime=" + createTime + ", createBy=" + createBy
				+ ", changeTime=" + changeTime + ", changeBy=" + changeBy + "]";
	}
    
}
