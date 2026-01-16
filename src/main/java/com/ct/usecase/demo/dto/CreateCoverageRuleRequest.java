package com.ct.usecase.demo.dto;

public record CreateCoverageRuleRequest(
        String serviceCode,
        boolean covered,
        boolean priorAuthRequired,
        Integer minAge,
        Integer maxAge
) {}

