package aitt.trainingapp_backend.service.impl;

import aitt.trainingapp_backend.service.RoleHierarchyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

@Service
@Primary
public class RoleHierarchyServiceImpl extends RoleHierarchyImpl implements RoleHierarchyService {
    private static final Logger logger = LoggerFactory.getLogger(RoleHierarchyServiceImpl.class);
    public RoleHierarchyServiceImpl() {
        String hierarchy = "ROLE_ADMIN > ROLE_COACH\nROLE_COACH > ROLE_USER";
        logger.info("Setting role hierarchy: {}", hierarchy);
        this.setHierarchy(hierarchy);
        logger.info("Role hierarchy set successfully");
    }
}