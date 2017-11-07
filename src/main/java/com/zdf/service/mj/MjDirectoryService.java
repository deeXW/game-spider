package com.zdf.service.mj;

import com.zdf.bean.mj.MjDirectory;

public interface MjDirectoryService {
	public boolean update(final MjDirectory mjDirectory);

	/**
	 * 根据主键id获取信息
	 * @param id
	 * @return
	 */
	public MjDirectory getById(Long id);
}
