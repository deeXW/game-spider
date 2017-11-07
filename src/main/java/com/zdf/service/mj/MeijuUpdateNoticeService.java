package com.zdf.service.mj;

import java.util.List;

import com.zdf.bean.mj.MeijuUpdateNotice;

public interface MeijuUpdateNoticeService {
	/**
     * 根据类型获取对应的信息
     * @param type
     * @return
     */
    public List<MeijuUpdateNotice> getMeijuUpdateNoticeListByType(String type,String filmType);
    
    /**
     * 更新集数
     * @return
     */
    public boolean updateCount(MeijuUpdateNotice meijuUpdateNotice);
}
