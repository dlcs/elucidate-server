package com.digirati.elucidate.infrastructure.database.rowmapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.model.security.SecurityUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SecurityUserRowMapper implements RowMapper<SecurityUser> {
    @Override
    public SecurityUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        SecurityUser user = new SecurityUser();
        user.setPk(ResultSetUtils.getInt(rs, "id"));
        user.setUid(ResultSetUtils.getString(rs, "uid"));
        user.setId(ResultSetUtils.getString(rs, "security_user_id"));
        return user;
    }
}
