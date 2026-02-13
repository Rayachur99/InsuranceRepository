package com.ct.usecase.demo.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateCoverageRuleRequest(
        boolean covered,
        boolean priorAuthRequired,
        @NotNull
        Integer minAge,
        @NotNull
        Integer maxAge
) {}

