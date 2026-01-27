package com.ct.usecase.demo.dto;

public record UpdateCoverageRuleRequest(
        boolean covered,
        boolean priorAuthRequired,
        Integer minAge,
        Integer maxAge
) {}

