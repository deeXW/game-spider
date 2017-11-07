package com.zdf.service.mj.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdf.bean.mj.MjDirectory;
import com.zdf.dao.mj.MjDirectoryDao;
import com.zdf.service.mj.MjDirectoryService;
@Service
public class MjDirectoryServiceImpl implements MjDirectoryService {
	@Autowired
	private MjDirectoryDao mjDirectoryDao;
	
	@Override
	public boolean update(MjDirectory mjDirectory) {
		return mjDirectoryDao.update(mjDirectory);
	}

	@Override
	public MjDirectory getById(Long id) {
		return mjDirectoryDao.getById(id);
	}

}
