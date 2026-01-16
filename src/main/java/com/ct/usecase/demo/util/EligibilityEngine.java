package com.ct.usecase.demo.util;


import java.time.LocalDate;

import com.ct.usecase.demo.dto.EligibilityDecisionModel;
import com.ct.usecase.demo.entity.CoverageRuleEntity;
import com.ct.usecase.demo.entity.MemberEntity;

public class EligibilityEngine {

    public EligibilityDecisionModel evaluate(
            MemberEntity member,
            CoverageRuleEntity rule,
            LocalDate requestDate
    ) {

        if (requestDate.isBefore(member.getCoverageStartDate())
                || requestDate.isAfter(member.getCoverageEndDate())) {
            return new EligibilityDecisionModel(
                    false, false, "COVERAGE_INACTIVE"
            );
        }

        if (rule == null || !rule.isCovered()) {
            return new EligibilityDecisionModel(
                    false, false, "SERVICE_NOT_COVERED"
            );
        }

        return new EligibilityDecisionModel(
                true,
                rule.isPriorAuthRequired(),
                rule.isPriorAuthRequired()
                        ? "PRIOR_AUTH_REQUIRED"
                        : "ELIGIBLE"
        );
    }
}
