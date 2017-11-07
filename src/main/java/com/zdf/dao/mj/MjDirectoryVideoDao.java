package com.zdf.dao.mj;

import java.util.List;

import com.zdf.bean.mj.MjDirectoryVideo;

public interface MjDirectoryVideoDao {
	/**
	 * 根据目录id获取视频链接
	 * @param DirectoryId
	 * @return
	 */
	public List<MjDirectoryVideo> getVideoListByDirectoryId(Long directoryId);
	
	/**
	 * 获取目录最大排序号
	 * @param directoryId
	 * @return
	 */
	public Long getMaxSort(Long directoryId);
	
	public boolean insert(final MjDirectoryVideo mjDirectoryVideo);
}
