package com.ct.usecase.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.usecase.demo.dto.CreateOrgRequest;
import com.ct.usecase.demo.dto.CreateUserRequest;
import com.ct.usecase.demo.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @PostMapping("/organizations")
    public ResponseEntity<?> createOrganization(@RequestBody CreateOrgRequest request) {

        log.info("Create organization request received. name={}, type={}",
                request.name(), request.type());

        Object response = adminService.createOrganization(request.name(), request.type());

        log.info("Organization created successfully. name={}", request.name());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {

        log.info("Create user request received. username={}, role={}, orgId={}",
                request.username(), request.role(), request.organizationId());

        Object response = adminService.createUser(
                request.username(),
                request.password(), // NOT LOGGED
                request.role(),
                request.organizationId()
        );

        log.info("User created successfully. username={}", request.username());

        return ResponseEntity.ok(response);
    }

}

