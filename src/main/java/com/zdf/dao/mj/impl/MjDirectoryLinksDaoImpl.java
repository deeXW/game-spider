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

import com.zdf.bean.mj.MjDirectoryLinks;
import com.zdf.bean.mj.MjDirectoryVideo;
import com.zdf.dao.base.AbstractBaseDaoImpl;
import com.zdf.dao.mj.MjDirectoryLinksDao;

@Repository
public class MjDirectoryLinksDaoImpl extends AbstractBaseDaoImpl<MjDirectoryLinks> implements MjDirectoryLinksDao{
	private final Logger logger = Logger.getLogger(MjDirectoryLinksDaoImpl.class);
	@Override
	public Integer getLinksCount(Long directoryId) {
		String sql="select count(1) from mj_directory_links where is_subtitle = '1' and LENGTH(download_link) > 0 and directory_id=? ";
		return getJdbcTemplate().queryForInt(sql, directoryId);
	}

	@Override
    public List<MjDirectoryLinks> getLinksListByParams(Long directoryId,String isSubtitle) {
        final List<MjDirectoryLinks> directoryLinksList = new ArrayList<MjDirectoryLinks>();
        try {
            String sql = "SELECT * FROM mj_directory_links " +
            			 "where is_subtitle = ? and directory_id = ? order by sort ";
            ArrayList<Object> paramList = new ArrayList<Object>();
            paramList.add(isSubtitle);
            paramList.add(directoryId);
            getJdbcTemplate().query(sql, paramList.toArray(), new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                	MjDirectoryLinks directoryLinks = new MjDirectoryLinks();
                	directoryLinks.setId(rs.getLong("id"));
                	directoryLinks.setDirectoryId(rs.getLong("directory_id"));
                	directoryLinks.setSort(rs.getInt("sort"));
                	directoryLinks.setDownloadName(rs.getString("download_name"));
                	directoryLinks.setDownloadLink(rs.getString("download_link"));
                	directoryLinks.setDownloadName720(rs.getString("download_name_720"));
                	directoryLinks.setDownloadLink720(rs.getString("download_link_720"));
                	directoryLinks.setCreateTime(rs.getDate("create_time"));
                	directoryLinks.setCreateBy(rs.getString("create_by"));
                	directoryLinks.setChangeTime(rs.getDate("change_time"));
                	directoryLinks.setChangeBy(rs.getString("change_by"));
                	directoryLinksList.add(directoryLinks);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        return directoryLinksList;
    }
	
	//LENGTH(download_link) > 0
	@Override
	public Long getMaxSort(Long directoryId,String isSubtitle){
		try {
            String sql = "SELECT max(sort) FROM mj_directory_links " +
            			 "where is_subtitle = ? and directory_id = ?";
            return getJdbcTemplate().queryForLong(sql, new Object[] { isSubtitle,directoryId });
        } catch (Exception e) {
        	logger.error(e.getMessage());
        	return 0l;
        }
	}
	
	
	@Override
    public boolean insert(final MjDirectoryLinks mjDirectoryLinks){
        boolean flag = false;
                                  
//        String sql = "INSERT INTO mj_directory_links(sort,directory_id,download_name,download_link,is_subtitle,pixel,create_time,create_by)" +
//				" VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sql = "INSERT INTO mj_directory_links(sort,directory_id,download_name,download_link," +
					"download_name_720,download_link_720,is_distinguish,is_subtitle,pixel," +
					"create_time,create_by,change_time,change_by)" +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?)";
        try{
            int count = getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setLong(1,mjDirectoryLinks.getSort());
                    ps.setLong(2,mjDirectoryLinks.getDirectoryId());
                    ps.setString(3,mjDirectoryLinks.getDownloadName());
                    ps.setString(4, mjDirectoryLinks.getDownloadLink());
                    ps.setString(5, mjDirectoryLinks.getDownloadName720());
                    ps.setString(6, mjDirectoryLinks.getDownloadLink720());
//                    ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                    ps.setString(7, mjDirectoryLinks.getIsDistinguish());
                    ps.setString(8, mjDirectoryLinks.getIsSubtitle());
                    ps.setString(9, mjDirectoryLinks.getPixel());
                    ps.setString(10, "admin");
                    ps.setString(11, "admin");
                }
            });
            flag = count > 0 ? true : false;
        }catch (Exception e){
        	logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return flag;
    }
	
	@Override
	public boolean update(final MjDirectoryLinks mjDirectoryLinks){
		boolean flag = false;
		
		String sql = "update mj_directory_links " +
					 "set download_name = ?, " +
					 "download_link = ?, " +
					 "change_time = ?, " +
					 "change_by = ? " +
					 "where id = ? ";
		try{
			int count = getJdbcTemplate().update(sql, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1,mjDirectoryLinks.getDownloadName());
					ps.setString(2, mjDirectoryLinks.getDownloadLink());
					ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
					ps.setString(4, "admin");
					ps.setLong(5, mjDirectoryLinks.getId());
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
