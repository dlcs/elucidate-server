package com.digirati.elucidate.service.security.impl;

import com.digirati.elucidate.infrastructure.generator.IDGenerator;
import com.digirati.elucidate.infrastructure.security.Permission;
import com.digirati.elucidate.infrastructure.security.UserSecurityDetailsContext;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.security.SecurityGroup;
import com.digirati.elucidate.repository.security.GroupRepository;
import com.digirati.elucidate.service.security.SecurityGroupService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(SecurityGroupServiceImpl.SERVICE_NAME)
public class SecurityGroupServiceImpl implements SecurityGroupService {

    public static final String SERVICE_NAME = "securityGroupServiceImpl";
    private final GroupRepository groupRepository;
    private final IDGenerator idGenerator;
    private UserSecurityDetailsContext securityDetailsContext;

    public SecurityGroupServiceImpl(
        UserSecurityDetailsContext securityDetailsContext,
        GroupRepository groupRepository,
        @Qualifier("groupIdGenerator") IDGenerator idGenerator
    ) {
        this.securityDetailsContext = securityDetailsContext;
        this.groupRepository = groupRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public ServiceResponse<SecurityGroup> createGroup(String label) {
        String id = idGenerator.generateId();
        SecurityGroup group = groupRepository.createGroup(securityDetailsContext.getAuthenticationId(), id, label);

        return new ServiceResponse<>(Status.OK, group);
    }

    @Override
    public ServiceResponse<SecurityGroup> getGroup(String id) {
        return groupRepository.getGroup(id)
            .map(group -> {
                if (!securityDetailsContext.isAuthorized(Permission.READ, group)) {
                    return new ServiceResponse<SecurityGroup>(Status.UNAUTHORIZED, null);
                }

                return new ServiceResponse<>(Status.OK, group);
            })
            .orElseGet(() -> new ServiceResponse<>(Status.NOT_FOUND, null));
    }
}
