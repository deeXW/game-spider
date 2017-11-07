package com.zdf.webspider.sql.bean;

public class SqlMeijuttBean {

	private String url;
	private String directoryId;
	private String rowNum;
	
	public SqlMeijuttBean() {
	}

	public SqlMeijuttBean(String directoryId, String url, String rowNum) {
		super();
		this.directoryId = directoryId;
		this.url = url;
		this.rowNum = rowNum;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDirectoryId() {
		return directoryId;
	}

	public void setDirectoryId(String directoryId) {
		this.directoryId = directoryId;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

}
