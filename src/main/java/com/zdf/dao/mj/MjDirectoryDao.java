package com.zdf.dao.mj;

import com.zdf.bean.mj.MjDirectory;

public interface MjDirectoryDao {
	public boolean update(final MjDirectory mjDirectory);

	/**
	 * 根据主键id获取信息
	 * @param id
	 * @return
	 */
	public MjDirectory getById(Long id);
}
