package com.zdf.webspider.processor.mj.bean;

public class ZimuzuProcessorBean {
	private String season;
	private String format;
	private String url;
	
	public ZimuzuProcessorBean() {
	}
	
	public ZimuzuProcessorBean(String season, String format, String url) {
		super();
		this.season = season;
		this.format = format;
		this.url = url;
	}

	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "ZimuzuProcessorBean [season=" + season + ", format=" + format
				+ ", url=" + url + "]";
	}
	
}
