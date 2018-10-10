package com.digirati.elucidate.repository.security.impl;

import com.digirati.elucidate.infrastructure.database.rowmapper.AnnotationReferenceRowMapper;
import com.digirati.elucidate.infrastructure.database.rowmapper.SecurityUserReferenceRowMapper;
import com.digirati.elucidate.model.annotation.AnnotationReference;
import com.digirati.elucidate.model.security.SecurityUserReference;
import com.digirati.elucidate.repository.security.GroupMembershipRepository;
import java.util.List;
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
    public List<AnnotationReference> getAnnotationGroupMemberships(int groupPk) {
        String sql = "select * from security_group_annotations where groupid = ?";
        Object[] params = {groupPk,};
        int[] sqlTypes = {Types.INTEGER,};

        return jdbcTemplate.query(sql, params, sqlTypes, new AnnotationReferenceRowMapper());
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

    @Override
    public List<SecurityUserReference> getUserGroupMemberships(int groupPk) {
        String sql = "select * from security_group_users where groupid = ?";
        Object[] params = {groupPk,};
        int[] sqlTypes = {Types.INTEGER,};

        return jdbcTemplate.query(sql, params, sqlTypes, new SecurityUserReferenceRowMapper());
    }
}
