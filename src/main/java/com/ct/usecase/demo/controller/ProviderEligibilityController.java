package com.ct.usecase.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.usecase.demo.dto.EligibilityCheckRequest;
import com.ct.usecase.demo.dto.EligibilityCheckResponse;
import com.ct.usecase.demo.service.EligibilityService;
import com.ct.usecase.demo.util.SecurityContextUtil;

@RestController
@RequestMapping("/provider/eligibility")
public class ProviderEligibilityController {

    private final EligibilityService eligibilityService;

    public ProviderEligibilityController(
            EligibilityService eligibilityService
    ) {
        this.eligibilityService = eligibilityService;
    }

    @PostMapping("/check")
    public EligibilityCheckResponse checkEligibility(
            @RequestBody EligibilityCheckRequest request
    ) {
        Long providerOrgId = SecurityContextUtil.getCurrentOrgId();
        return eligibilityService.checkEligibility(providerOrgId, request);
    }
}
