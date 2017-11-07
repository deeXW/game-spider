package com.zdf.dao.mj.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.zdf.bean.mj.MjDirectory;
import com.zdf.dao.base.AbstractBaseDaoImpl;
import com.zdf.dao.mj.MjDirectoryDao;


@Repository
public class MjDirectoryDaoImpl extends AbstractBaseDaoImpl<MjDirectory>  implements MjDirectoryDao {
	@Override
    public boolean update(final MjDirectory mjDirectory) {
        boolean flag = false;
        String sql = "UPDATE mj_directory " +
                     "set update_count = ?, " +
                     "create_time = ? ," +
                     "change_time = ? " +
                     "WHERE id = ?";
        try {
            int count = getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, mjDirectory.getUpdateCount());
                    ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                    ps.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
                    ps.setLong(4, mjDirectory.getId());
                }
            });
            flag = count > 0 ? true : false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    @Override
    public MjDirectory getById(Long id) {
        final MjDirectory mjDirectory = new MjDirectory();
        try {
            String sql = "SELECT * FROM mj_directory " +
                    "where id = ?";
            ArrayList<Object> paramList = new ArrayList<Object>();
            paramList.add(id);
            getJdbcTemplate().query(sql, paramList.toArray(), new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    mjDirectory.setId(rs.getLong("id"));
                    mjDirectory.setTitle(rs.getString("title"));
                    mjDirectory.setPureTitle(rs.getString("pure_title"));
                    mjDirectory.setContentDesc(rs.getString("content_desc"));
                    mjDirectory.setFilmType(rs.getString("film_type"));
                    mjDirectory.setType(rs.getString("type"));
                    mjDirectory.setTypeDesc(rs.getString("type_desc"));
                    mjDirectory.setBroadcastFrom(rs.getString("broadcast_from"));
                    mjDirectory.setArea(rs.getString("area"));
                    mjDirectory.setStarring(rs.getString("starring"));
                    mjDirectory.setLanguage(rs.getString("language"));
                    mjDirectory.setImgUrl(rs.getString("img_url"));
                    mjDirectory.setPremiereTime(rs.getString("premiere_time"));
                    mjDirectory.setEnglishName(rs.getString("english_name"));
                    mjDirectory.setAlias(rs.getString("alias"));
                    mjDirectory.setBroadcastTime(rs.getString("broadcast_time"));
                    mjDirectory.setTotalCount(rs.getString("total_count"));
                    mjDirectory.setIsUpdate(rs.getString("is_update"));
                    mjDirectory.setUpdateCount(rs.getString("update_count"));
                    mjDirectory.setSort(rs.getString("sort"));
                    mjDirectory.setDownloadCount(rs.getLong("download_count"));
                }
            });
        } catch (Exception e) {
            return null;
        }
        return mjDirectory;
    }
}
