package com.ct.usecase.demo.util;


import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.ct.usecase.demo.dto.EligibilityDecisionModel;
import com.ct.usecase.demo.entity.CoverageRuleEntity;
import com.ct.usecase.demo.entity.MemberEntity;
import com.ct.usecase.demo.enums.EligibilityOutcome;

@Component
public class EligibilityEngine {

    public EligibilityDecisionModel evaluate(
            MemberEntity member,
            CoverageRuleEntity rule,
            LocalDate requestDate
    ) {

        if (requestDate.isBefore(member.getCoverageStartDate())
                || requestDate.isAfter(member.getCoverageEndDate())) {

            return new EligibilityDecisionModel(
                    false,
                    false,
                    EligibilityOutcome.COVERAGE_INACTIVE
            );
        }

        if (rule == null || !rule.isCovered()) {

            return new EligibilityDecisionModel(
                    false,
                    false,
                    EligibilityOutcome.SERVICE_NOT_COVERED
            );
        }

        if (rule.isPriorAuthRequired()) {
            return new EligibilityDecisionModel(
                    true,
                    true,
                    EligibilityOutcome.PRIOR_AUTH_REQUIRED
            );
        }

        return new EligibilityDecisionModel(
                true,
                false,
                EligibilityOutcome.ELIGIBLE
        );
    }
}
