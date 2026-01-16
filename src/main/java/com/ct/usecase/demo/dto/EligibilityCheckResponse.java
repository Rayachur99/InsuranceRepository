package com.ct.usecase.demo.dto;

public record EligibilityCheckResponse(
        boolean eligible,
        boolean priorAuthRequired,
        String reasonCode
) {}
