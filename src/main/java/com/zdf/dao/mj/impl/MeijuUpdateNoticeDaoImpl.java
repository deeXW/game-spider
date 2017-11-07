package com.zdf.dao.mj.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.zdf.bean.mj.MeijuUpdateNotice;
import com.zdf.dao.base.AbstractBaseDaoImpl;
import com.zdf.dao.mj.MeijuUpdateNoticeDao;

@Repository("meijuUpdateNoticeDao")
public class MeijuUpdateNoticeDaoImpl extends AbstractBaseDaoImpl<MeijuUpdateNotice> implements
                                                                                    MeijuUpdateNoticeDao {
    private final Logger logger = Logger.getLogger(MeijuUpdateNoticeDaoImpl.class);

    @Override
    public List<MeijuUpdateNotice> getMeijuUpdateNoticeListByType(String type,String filmType) {
        final List<MeijuUpdateNotice> meijuUpdateNoticeList = new ArrayList<MeijuUpdateNotice>();
        try {
            String sql = "select * from meiju_update_notice WHERE type = ? and film_type = ? ";
            ArrayList<Object> paramList = new ArrayList<Object>();
            paramList.add(type);
            paramList.add(filmType);
            getJdbcTemplate().query(sql, paramList.toArray(), new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    MeijuUpdateNotice meijuUpdateNotice = new MeijuUpdateNotice();
                    meijuUpdateNotice.setId(rs.getLong("id"));
                    meijuUpdateNotice.setType(rs.getString("type"));
                    meijuUpdateNotice.setLink(rs.getString("link"));
                    meijuUpdateNotice.setDirectoryId(rs.getLong("directory_id"));
                    meijuUpdateNotice.setDirectoryName(rs.getString("directory_name"));
                    meijuUpdateNotice.setOriginalUpdateCount(rs.getString("original_update_count"));
                    meijuUpdateNotice.setNowUpdateCount(rs.getString("now_update_count"));
                    meijuUpdateNotice.setExtContent(rs.getString("ext_content"));
                    meijuUpdateNotice.setCreateTime(rs.getDate("create_time"));
                    meijuUpdateNotice.setCreateBy(rs.getString("create_by"));
                    meijuUpdateNotice.setChangeTime(rs.getDate("change_time"));
                    meijuUpdateNotice.setChangeBy(rs.getString("change_by"));
                    meijuUpdateNoticeList.add(meijuUpdateNotice);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        return meijuUpdateNoticeList;
    }

    @Override
    public boolean updateCount(final MeijuUpdateNotice meijuUpdateNotice) {
        boolean flag = false;
        String sql = "UPDATE meiju_update_notice "
                     + "set original_update_count = now_update_count, now_update_count = ? ,change_time = ? "
                     + "WHERE id = ?";
        try {
            int count = getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, meijuUpdateNotice.getNowUpdateCount());
//                    ps.setDate(2, new java.sql.Date(meijuUpdateNotice.getChangeTime().getTime()));
                    ps.setTimestamp(2, new java.sql.Timestamp(meijuUpdateNotice.getChangeTime().getTime()));
                    ps.setLong(3, meijuUpdateNotice.getId());
                }
            });
            flag = count > 0 ? true : false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

}
