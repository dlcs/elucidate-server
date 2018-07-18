package com.digirati.elucidate.repository.security.impl;

import com.digirati.elucidate.common.repository.impl.AbstractRepositoryJDBCImpl;
import com.digirati.elucidate.infrastructure.database.rowmapper.SecurityGroupRowMapper;
import com.digirati.elucidate.model.security.SecurityGroup;
import com.digirati.elucidate.repository.security.GroupRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository(GroupRepositoryJDBCImpl.REPOSITORY_NAME)
public class GroupRepositoryJDBCImpl extends AbstractRepositoryJDBCImpl implements GroupRepository {
    static final String REPOSITORY_NAME = "groupRepositoryJDBCImpl";

    protected GroupRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public List<SecurityGroup> getGroupsByUserId(Integer userPk) {
        String sql = "SELECT * FROM security_group_get_by_user(?)";
        Object[] params = {userPk};
        int[] sqlTypes = {Types.INTEGER};

        return queryForList(sql, params, sqlTypes, new SecurityGroupRowMapper());
    }

    @Override
    public SecurityGroup createGroup(Integer ownerPk, String groupId, String label) {
        String sql = "SELECT * FROM security_group_create(?, ?, ?)";
        Object[] params = {ownerPk, groupId, label};
        int[] sqlTypes = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR};

        return queryForObject(sql, params, sqlTypes, new SecurityGroupRowMapper());
    }

    @Override
    public Optional<SecurityGroup> getGroup(String groupId) {
        String sql = "SELECT sg.* FROM security_group sg WHERE sg.group_id = ?";
        Object[] params = {groupId,};
        int[] sqlTypes = {Types.VARCHAR,};

        return Optional.ofNullable(queryForObject(sql, params, sqlTypes, new SecurityGroupRowMapper()));
    }
}
