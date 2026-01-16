package com.ct.usecase.demo.dto;

import com.ct.usecase.demo.util.OrganizationType;

public record CreateOrgRequest(String name, OrganizationType type) {}
