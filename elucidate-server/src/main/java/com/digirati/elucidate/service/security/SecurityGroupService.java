package com.digirati.elucidate.service.security;

import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.security.SecurityGroup;
import java.util.List;

public interface SecurityGroupService {
    ServiceResponse<SecurityGroup> createGroup(String label);

    ServiceResponse<SecurityGroup> getGroup(String id);
}
