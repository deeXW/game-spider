package com.zdf.service.mj.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdf.bean.mj.MeijuUpdateNotice;
import com.zdf.dao.mj.MeijuUpdateNoticeDao;
import com.zdf.service.mj.MeijuUpdateNoticeService;

@Service
public class MeijuUpdateNoticeServiceImpl implements MeijuUpdateNoticeService {
	@Autowired
	private MeijuUpdateNoticeDao meijuUpdateNoticeDao;
	
	@Override
	public List<MeijuUpdateNotice> getMeijuUpdateNoticeListByType(String type,String filmType) {
		return meijuUpdateNoticeDao.getMeijuUpdateNoticeListByType(type,filmType);
	}


	@Override
	public boolean updateCount(MeijuUpdateNotice meijuUpdateNotice) {
		meijuUpdateNotice.setChangeTime(new Date());
		return meijuUpdateNoticeDao.updateCount(meijuUpdateNotice);
	}
	
	
}

