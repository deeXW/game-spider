package com.zdf.bean.mj;

import java.util.Date;

public class MeijuUpdateNotice {
    private Long   id;

    /**
     * 网站类型 1:cn163；2:meijutt
     */
    private String type;

    /**
     * 链接地址
     */
    private String link;

    /**
     * 目录id
     */
    private Long   directoryId;

    /**
     * 美剧名称
     */
    private String directoryName;

    /**
     * 更新数据-针对三方网站 原有
     */
    private String originalUpdateCount;

    /**
     * 更新数据-针对三方网站 新更新
     */
    private String nowUpdateCount;

    /**
     * 扩展内容 按时间情况定义内容
     */
    private String extContent;
    
    private Date   createTime;

    private String createBy;

    private Date   changeTime;

    private String changeBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(Long directoryId) {
        this.directoryId = directoryId;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getOriginalUpdateCount() {
        return originalUpdateCount;
    }

    public void setOriginalUpdateCount(String originalUpdateCount) {
        this.originalUpdateCount = originalUpdateCount;
    }

    public String getNowUpdateCount() {
        return nowUpdateCount;
    }

    public void setNowUpdateCount(String nowUpdateCount) {
        this.nowUpdateCount = nowUpdateCount;
    }

    public String getExtContent() {
		return extContent;
	}

	public void setExtContent(String extContent) {
		this.extContent = extContent;
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
        return "MeijuUpdateNotice [id=" + id + ", type=" + type + ", link=" + link
               + ", directoryId=" + directoryId + ", directoryName=" + directoryName
               + ", originalUpdateCount=" + originalUpdateCount + ", nowUpdateCount="
               + nowUpdateCount + ", createTime=" + createTime + ", createBy=" + createBy
               + ", changeTime=" + changeTime + ", changeBy=" + changeBy + "]";
    }


}
