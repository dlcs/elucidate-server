package com.digirati.elucidate.repository.security.impl;

import com.digirati.elucidate.repository.security.GroupMembershipRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository(GroupMembershipRepositoryJDBCImpl.REPOSITORY_NAME)
public class GroupMembershipRepositoryJDBCImpl implements GroupMembershipRepository {
    static final String REPOSITORY_NAME = "groupMembershipRepositoryJDBCImpl";

    private final JdbcTemplate jdbcTemplate;

    protected GroupMembershipRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createAnnotationGroupMembership(int annotationPk, int groupPk) {
        String sql = "select * from security_group_add_annotation(?, ?)";
        Object[] params = {groupPk, annotationPk,};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER,};

        jdbcTemplate.query(sql, params, sqlTypes, rs -> {
        });
    }

    @Override
    public void removeAnnotationGroupMembership(int annotationPk, int groupPk) {
        String sql = "select * from security_group_remove_annotation(?, ?)";
        Object[] params = {groupPk, annotationPk,};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER,};

        jdbcTemplate.query(sql, params, sqlTypes, rs -> {
        });
    }

    @Override
    public void createUserGroupMembership(int userPk, int groupPk) {
        String sql = "select * from security_group_add_user(?, ?)";
        Object[] params = {groupPk, userPk,};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER,};

        jdbcTemplate.query(sql, params, sqlTypes, rs -> {
        });
    }

    @Override
    public void removeUserGroupMembership(int userPk, int groupPk) {
        String sql = "select * from security_group_remove_user(?, ?)";
        Object[] params = {groupPk, userPk,};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER,};

        jdbcTemplate.query(sql, params, sqlTypes, rs -> {
        });
    }
}
