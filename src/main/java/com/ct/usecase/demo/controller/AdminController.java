package com.ct.usecase.demo.controller;

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

    @PostMapping("/organizations")
    public ResponseEntity<?> createOrganization(@RequestBody CreateOrgRequest request) {
        return ResponseEntity.ok(
                adminService.createOrganization(request.name(), request.type())
        );
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(
                adminService.createUser(
                        request.username(),
                        request.password(),
                        request.role(),
                        request.organizationId()
                )
        );
    }
}

