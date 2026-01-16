package com.ct.usecase.demo.dto;

import com.ct.usecase.demo.util.UserRole;

public record CreateUserRequest(
        String username,
        String password,
        UserRole role,
        Long organizationId
) {}

