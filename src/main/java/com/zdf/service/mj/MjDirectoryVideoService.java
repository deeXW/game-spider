package com.zdf.service.mj;

import java.util.List;
import java.util.Map;

import com.zdf.bean.mj.MjDirectoryVideo;

public interface MjDirectoryVideoService {
	/**
	 * 根据目录id获取视频链接
	 * @param DirectoryId
	 * @return
	 */
	public List<MjDirectoryVideo> getVideoListByDirectoryId(Long directoryId);
	
	/**
	 * 根据目录id获取视频链接
	 * @param DirectoryId
	 * @return map
	 */
	public Map<Integer,MjDirectoryVideo> getVideoMapByDirectoryId(Long directoryId);
	
	/**
	 * 获取目录最大排序号
	 * @param directoryId
	 * @return
	 */
	public Long getMaxSort(Long directoryId);
	
	public boolean insert(final MjDirectoryVideo mjDirectoryVideo);
}
