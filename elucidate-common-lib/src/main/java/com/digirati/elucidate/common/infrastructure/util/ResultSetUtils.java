package com.digirati.elucidate.common.infrastructure.util;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.jsonldjava.utils.JsonUtils;

public class ResultSetUtils {

    public static String getString(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
    }

    public static boolean getBoolean(ResultSet rs, String columnName) throws SQLException {
        return rs.getBoolean(columnName);
    }

    public static Date getDate(ResultSet rs, String columnName) throws SQLException {
        return rs.getTimestamp(columnName);
    }

    public static int getInt(ResultSet rs, String columnName) throws SQLException {
        return rs.getInt(columnName);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getJsonMap(ResultSet rs, String columnName) throws SQLException {

        String jsonStr = getString(rs, columnName);
        if (StringUtils.isNotBlank(jsonStr)) {
            try {
                Object jsonObject = JsonUtils.fromString(jsonStr);
                if (jsonObject instanceof List) {
                    return ((List<Map<String, Object>>) jsonObject).get(0);
                } else if (jsonObject instanceof Map) {
                    return (Map<String, Object>) jsonObject;
                } else {
                    throw new SQLException(String.format("Unexpected JSON Object type [%s]", jsonObject.getClass()));
                }
            } catch (IOException e) {
                throw new SQLException(String.format("Unable to parse JSON String [%s] into Map", jsonStr), e);
            }
        }

        return null;
    }
}
