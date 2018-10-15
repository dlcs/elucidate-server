package com.digirati.elucidate.repository.security.impl;

import com.digirati.elucidate.common.repository.impl.AbstractRepositoryJDBCImpl;
import com.digirati.elucidate.infrastructure.database.rowmapper.SecurityUserRowMapper;
import com.digirati.elucidate.model.security.SecurityUser;
import com.digirati.elucidate.repository.security.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Optional;

@Repository(UserRepositoryJDBCImpl.REPOSITORY_NAME)
public class UserRepositoryJDBCImpl extends AbstractRepositoryJDBCImpl implements UserRepository {

    static final String REPOSITORY_NAME = "userRepositoryJDBCImpl";

    protected UserRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SecurityUser> getUser(String uid) {
        String sql = "SELECT u.* FROM security_user u WHERE u.uid = ?";
        Object[] params = {uid,};
        int[] sqlTypes = {Types.VARCHAR,};

        return Optional.ofNullable(queryForObject(sql, params, sqlTypes, new SecurityUserRowMapper()));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SecurityUser> getUserById(String id) {
        String sql = "SELECT u.* FROM security_user u WHERE u.security_user_id = ?";
        Object[] params = {id,};
        int[] sqlTypes = {Types.VARCHAR,};

        return Optional.ofNullable(queryForObject(sql, params, sqlTypes, new SecurityUserRowMapper()));
    }

    @Override
    @Transactional(readOnly = false)
    public SecurityUser createUser(String id, String uid) {
        String sql = "SELECT * FROM security_user_create(?, ?)";
        Object[] params = {id, uid,};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR,};

        return queryForObject(sql, params, sqlTypes, new SecurityUserRowMapper());
    }
}
