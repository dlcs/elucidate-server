package com.digirati.elucidate.infrastructure.database.rowmapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.model.security.SecurityGroup;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SecurityGroupRowMapper implements RowMapper<SecurityGroup> {
    @Override
    public SecurityGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
        SecurityGroup sg = new SecurityGroup();
        sg.setPk(ResultSetUtils.getInt(rs, "id"));
        sg.setLabel(ResultSetUtils.getString(rs, "label"));
        sg.setOwnerId(ResultSetUtils.getInt(rs, "owner_id"));
        sg.setId(ResultSetUtils.getString(rs, "group_id"));
        return sg;
    }
}
