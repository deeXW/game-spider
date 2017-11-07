package com.zdf.dao.mj;

import java.util.List;

import com.zdf.bean.mj.MjDirectoryLinks;

public interface MjDirectoryLinksDao {
	/**
	 * 获取有字幕且有下载连接 的记录数据
	 * @param directoryId
	 * @return Integer
	 */
	public Integer getLinksCount(Long directoryId);
	
	/**
	 * 获取对应目录下面的链接
	 * @param directoryId
	 * @param isSubtitle 是否有字幕 1 有 0 没有
	 * @return
	 */
	public List<MjDirectoryLinks> getLinksListByParams(Long directoryId,String isSubtitle);
	
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
