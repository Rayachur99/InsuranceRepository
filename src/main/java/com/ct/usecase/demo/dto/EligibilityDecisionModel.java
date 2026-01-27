package com.ct.usecase.demo.dto;

import com.ct.usecase.demo.enums.EligibilityOutcome;

public record EligibilityDecisionModel(
        boolean eligible,
        boolean priorAuthRequired,
        EligibilityOutcome outcome
) {}
