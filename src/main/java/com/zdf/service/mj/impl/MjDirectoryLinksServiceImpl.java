package com.zdf.service.mj.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdf.bean.mj.MjDirectoryLinks;
import com.zdf.dao.mj.MjDirectoryLinksDao;
import com.zdf.service.mj.MjDirectoryLinksService;

@Service
public class MjDirectoryLinksServiceImpl implements MjDirectoryLinksService {
	@Autowired
	private MjDirectoryLinksDao mjDirectoryLinksDao;
	
	@Override
	public Integer getLinksCount(Long directoryId) {
		return mjDirectoryLinksDao.getLinksCount(directoryId);
	}

	@Override
	public Map<String, MjDirectoryLinks> getLinksMapByParams(Long directoryId,
			String isSubtitle) {
		List<MjDirectoryLinks> list = mjDirectoryLinksDao.getLinksListByParams(directoryId, isSubtitle);
		Map<String,MjDirectoryLinks> map = new HashMap<String,MjDirectoryLinks>();
		if(null != list && list.size() > 0){
			for (MjDirectoryLinks mjDirectoryLinks : list) {
				map.put(mjDirectoryLinks.getDownloadLink().trim(), mjDirectoryLinks);
			}
		}
		return map;
	}

	@Override
	public Long getMaxSort(Long directoryId, String isSubtitle) {
		return mjDirectoryLinksDao.getMaxSort(directoryId, isSubtitle);
	}

	@Override
	public boolean insert(MjDirectoryLinks mjDirectoryLinks) {
		return mjDirectoryLinksDao.insert(mjDirectoryLinks);
	}

	@Override
	public boolean update(MjDirectoryLinks mjDirectoryLinks) {
		return mjDirectoryLinksDao.update(mjDirectoryLinks);
	}

}
