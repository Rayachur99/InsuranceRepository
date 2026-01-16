package com.ct.usecase.demo.dto;

public record EligibilityDecisionModel(
        boolean eligible,
        boolean priorAuthRequired,
        String reasonCode
) {}
