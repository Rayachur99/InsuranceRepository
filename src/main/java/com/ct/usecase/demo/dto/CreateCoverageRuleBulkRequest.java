package com.ct.usecase.demo.dto;

import java.util.List;

public record CreateCoverageRuleBulkRequest(List<CreateCoverageRuleRequest> rules) {
}
