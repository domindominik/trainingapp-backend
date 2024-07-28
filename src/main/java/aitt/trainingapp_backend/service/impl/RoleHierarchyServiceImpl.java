package aitt.trainingapp_backend.service.impl;

import aitt.trainingapp_backend.service.RoleHierarchyService;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Service;

@Service
public class RoleHierarchyServiceImpl extends RoleHierarchyImpl implements RoleHierarchyService {
    public RoleHierarchyServiceImpl() {
        String hierarchy = "ROLE_ADMIN > ROLE_COACH\nROLE_COACH > ROLE_USER";
        this.setHierarchy(hierarchy);
    }
}
