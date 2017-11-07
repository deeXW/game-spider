package com.zdf.service.mj;

import java.util.Map;

import com.zdf.bean.mj.MjDirectoryLinks;

public interface MjDirectoryLinksService {
	/**
	 * 获取有字幕且有下载连接 的记录数据
	 * @param directoryId
	 * @return Integer
	 */
	public Integer getLinksCount(Long directoryId);
	
	/**
	 * 根据目录id获取视频链接
	 * @param DirectoryId
	 * @return map
	 */
	public Map<String,MjDirectoryLinks> getLinksMapByParams(Long directoryId,String isSubtitle);
	
	/**
	 * 获取最大排序
	 * @param directoryId
	 * @param isSubtitle
	 * @return
	 */
	public Long getMaxSort(Long directoryId,String isSubtitle);
	
	
	public boolean insert(final MjDirectoryLinks mjDirectoryLinks);
	
	public boolean update(final MjDirectoryLinks mjDirectoryLinks);
}
