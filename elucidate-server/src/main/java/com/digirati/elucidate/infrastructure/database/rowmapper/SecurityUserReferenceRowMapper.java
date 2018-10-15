package com.digirati.elucidate.infrastructure.database.rowmapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.model.security.SecurityUserReference;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class SecurityUserReferenceRowMapper implements RowMapper<SecurityUserReference> {

    @Override
    public SecurityUserReference mapRow(ResultSet rs, int rowNum) throws SQLException {
        String id = ResultSetUtils.getString(rs, "security_user_id");
        String username = ResultSetUtils.getString(rs, "uid");

        return new SecurityUserReference(id, username);
    }
}
