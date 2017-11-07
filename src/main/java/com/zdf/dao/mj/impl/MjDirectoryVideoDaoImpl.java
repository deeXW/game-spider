package com.zdf.dao.mj.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.zdf.bean.mj.MjDirectoryVideo;
import com.zdf.dao.base.AbstractBaseDaoImpl;
import com.zdf.dao.mj.MjDirectoryVideoDao;

@Repository
public class MjDirectoryVideoDaoImpl extends AbstractBaseDaoImpl<MjDirectoryVideo> implements MjDirectoryVideoDao{
	private final Logger logger = Logger.getLogger(MjDirectoryVideoDaoImpl.class);
	
	@Override
    public List<MjDirectoryVideo> getVideoListByDirectoryId(Long directoryId) {
        final List<MjDirectoryVideo> directoryVideoList = new ArrayList<MjDirectoryVideo>();
        try {
            String sql = "select * from mj_directory_video WHERE directory_id = ? order by sort ";
            ArrayList<Object> paramList = new ArrayList<Object>();
            paramList.add(directoryId);
            getJdbcTemplate().query(sql, paramList.toArray(), new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                	MjDirectoryVideo directoryVideo = new MjDirectoryVideo();
                	directoryVideo.setId(rs.getLong("id"));
                	directoryVideo.setDirectoryId(rs.getLong("directory_id"));
                	directoryVideo.setSort(rs.getLong("sort"));
                	directoryVideo.setType(rs.getString("type"));
                	directoryVideo.setOrgUrl(rs.getString("org_url"));
                	directoryVideo.setVideoUrl(rs.getString("video_url"));
                	directoryVideo.setVideoName(rs.getString("video_name"));
                	directoryVideo.setCreateTime(rs.getDate("create_time"));
                	directoryVideo.setCreateBy(rs.getString("create_by"));
                	directoryVideo.setChangeTime(rs.getDate("change_time"));
                	directoryVideo.setChangeBy(rs.getString("change_by"));
                	directoryVideoList.add(directoryVideo);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        return directoryVideoList;
    }
	
	
	@Override
	public Long getMaxSort(Long directoryId){
		try {
            String sql = "SELECT max(sort) FROM mj_directory_video where directory_id = ?";
            return getJdbcTemplate().queryForLong(sql, new Object[] { directoryId });
        } catch (Exception e) {
        	logger.error(e.getMessage());
        	return 0l;
        }
	}
	
	
	@Override
    public boolean insert(final MjDirectoryVideo mjDirectoryVideo){
        boolean flag = false;
                                  
        String sql = "INSERT INTO mj_directory_video(directory_id,sort,type,org_url,video_url,video_name,create_time,create_by)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            int count = getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setLong(1,mjDirectoryVideo.getDirectoryId());
                    ps.setLong(2,mjDirectoryVideo.getSort());
                    ps.setString(3,mjDirectoryVideo.getType());
                    ps.setString(4, mjDirectoryVideo.getOrgUrl());
                    ps.setString(5, mjDirectoryVideo.getVideoUrl());
                    ps.setString(6, mjDirectoryVideo.getVideoName());
                    ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                    ps.setString(8, "admin");
                }
            });
            flag = count > 0 ? true : false;
        }catch (Exception e){
        	logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return flag;
    }
}
