package com.zdf.service.mj.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdf.bean.mj.MjDirectoryVideo;
import com.zdf.dao.mj.MjDirectoryVideoDao;
import com.zdf.service.mj.MjDirectoryVideoService;
@Service
public class MjDirectoryVideoServiceImpl implements MjDirectoryVideoService {
	@Autowired
	private MjDirectoryVideoDao mjDirectoryVideoDao;

	@Override
	public List<MjDirectoryVideo> getVideoListByDirectoryId(Long directoryId) {
		return mjDirectoryVideoDao.getVideoListByDirectoryId(directoryId);
	}

	@Override
	public Map<Integer,MjDirectoryVideo> getVideoMapByDirectoryId(Long directoryId) {
		List<MjDirectoryVideo> list = this.getVideoListByDirectoryId(directoryId);
		Map<Integer,MjDirectoryVideo> map = new HashMap<Integer,MjDirectoryVideo>();
		if(null != list && list.size() > 0){
			for (MjDirectoryVideo mjDirectoryVideo : list) {
				String indexJiShu = mjDirectoryVideo.getVideoName().replace("集", "").replace("备", "").trim();
				if(indexJiShu.length() > 2 && indexJiShu.lastIndexOf("-") != -1){
					indexJiShu = indexJiShu.substring(0,indexJiShu.lastIndexOf("-"));
				}
				map.put(Integer.valueOf(indexJiShu), mjDirectoryVideo);
			}
		}
		return map;
	}

	@Override
	public Long getMaxSort(Long directoryId) {
		return mjDirectoryVideoDao.getMaxSort(directoryId);
	}

	@Override
	public boolean insert(MjDirectoryVideo mjDirectoryVideo) {
		return mjDirectoryVideoDao.insert(mjDirectoryVideo);
	}
}
