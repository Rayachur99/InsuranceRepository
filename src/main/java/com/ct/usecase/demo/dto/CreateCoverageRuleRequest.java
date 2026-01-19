package com.ct.usecase.demo.dto;

import com.ct.usecase.demo.ServiceCode;

public record CreateCoverageRuleRequest(
        ServiceCode serviceCode,
        boolean covered,
        boolean priorAuthRequired,
        Integer minAge,
        Integer maxAge
) {}

