package com.digirati.elucidate.common.repository.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.digirati.elucidate.common.infrastructure.rowmapper.AbstractRowMapper;

public abstract class AbstractRepositoryJDBCImpl {

    protected final Logger LOGGER = Logger.getLogger(AbstractRepositoryJDBCImpl.class);

    private JdbcTemplate jdbcTemplate;

    protected AbstractRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected <T> T queryForClass(String sql, Object[] params, int[] sqlTypes, Class<T> clazz) {
        LOGGER.info(String.format("Executing SQL query [%s] with parameters [%s] and SQL types [%s] for class [%s]", sql, Arrays.toString(params), Arrays.toString(sqlTypes), clazz));
        return jdbcTemplate.queryForObject(sql, params, sqlTypes, clazz);
    }

    protected <T> T queryForObject(String sql, Object[] params, int[] sqlTypes, AbstractRowMapper<T> rowMapper) {
        List<T> results = queryForList(sql, params, sqlTypes, rowMapper);
        if (results.isEmpty()) {
            return null;
        } else if (results.size() == 1) {
            return results.get(0);
        } else {
            throw new IncorrectResultSizeDataAccessException(0, results.size());
        }
    }

    protected <T> List<T> queryForList(String sql, Object[] params, int[] sqlTypes, AbstractRowMapper<T> rowMapper) {
        LOGGER.info(String.format("Executing SQL query [%s] with parameters [%s] and SQL type [%s] using row mapper [%s]", sql, Arrays.toString(params), Arrays.toString(sqlTypes), rowMapper));
        return jdbcTemplate.query(sql, params, sqlTypes, rowMapper);
    }

    protected void update(String sql, Object[] params, int[] sqlTypes) {
        LOGGER.info(String.format("Executing SQL update [%s] with parameters [%s] and SQL types [%s]", sql, Arrays.toString(params), Arrays.toString(sqlTypes)));
        int rowsAffected = jdbcTemplate.update(sql, params, sqlTypes);
        LOGGER.info(String.format("SQL Update [%s] affected [%s] rows", sql, rowsAffected));
    }
}
