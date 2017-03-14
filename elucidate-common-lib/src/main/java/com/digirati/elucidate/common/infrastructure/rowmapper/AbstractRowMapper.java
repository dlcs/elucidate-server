package com.digirati.elucidate.common.infrastructure.rowmapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.github.jsonldjava.utils.JsonUtils;

public abstract class AbstractRowMapper<T> implements RowMapper<T> {

    protected String getString(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
    }

    protected String getString(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex);
    }

    protected boolean getBoolean(ResultSet rs, String columnName) throws SQLException {
        return rs.getBoolean(columnName);
    }

    protected Date getDate(ResultSet rs, String columnName) throws SQLException {
        return rs.getTimestamp(columnName);
    }

    protected int getInt(ResultSet rs, String columnName) throws SQLException {
        return rs.getInt(columnName);
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> getJsonMap(ResultSet rs, String columnName) throws SQLException {
        String jsonStr = getString(rs, columnName);
        try {
            Object jsonObject = JsonUtils.fromString(jsonStr);

            if (jsonObject instanceof List) {
                return ((List<Map<String, Object>>) jsonObject).get(0);
            }

            if (jsonObject instanceof Map) {
                return (Map<String, Object>) jsonObject;
            }

            throw new SQLException(String.format("Unexpected JSON Object type [%s]", jsonObject.getClass()));
        } catch (IOException e) {
            throw new SQLException(String.format("Unable to parse JSON String [%s] into Map", jsonStr), e);
        }
    }
}
