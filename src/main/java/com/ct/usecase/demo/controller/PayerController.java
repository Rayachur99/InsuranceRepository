package com.ct.usecase.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ct.usecase.demo.dto.CreateCoverageRuleBulkRequest;
import com.ct.usecase.demo.dto.CreatePlanRequest;
import com.ct.usecase.demo.entity.CoverageRuleEntity;
import com.ct.usecase.demo.entity.InsurancePlanEntity;
import com.ct.usecase.demo.exception.ConflictException;
import com.ct.usecase.demo.service.PayerPlanService;
import com.ct.usecase.demo.util.SecurityContextUtil;

@RestController
@RequestMapping("/payer")
public class PayerController {

	private final PayerPlanService payerPlanService;

	public PayerController(PayerPlanService payerPlanService) {
		this.payerPlanService = payerPlanService;
	}

	private static final Logger log = LoggerFactory.getLogger(PayerController.class);

	@PostMapping("/plans")
	public InsurancePlanEntity createPlan(@RequestBody CreatePlanRequest request) {

	    Long payerOrgId = SecurityContextUtil.getCurrentOrgId();

	    log.info("Create plan request. orgId={}, planCode={}", payerOrgId, request.planCode());

	    InsurancePlanEntity plan = payerPlanService.createPlan(
	            payerOrgId,
	            request.planCode(),
	            request.planName(),
	            request.validFrom(),
	            request.validTo()
	    );

	    log.info("Plan created successfully. orgId={}, planCode={}", payerOrgId, request.planCode());

	    return plan;
	}

	
	@GetMapping("/plans/{planCode}/rules")
	public List<CoverageRuleEntity> getCoverageRules(
	        @PathVariable String planCode) throws NotFoundException {

	    Long payerOrgId = SecurityContextUtil.getCurrentOrgId();

	    return payerPlanService.getCoverageRules(
	            payerOrgId,
	            planCode
	    );
	}


	
	@PostMapping("/plans/{planCode}/rules/bulk")
	@ResponseStatus(HttpStatus.CREATED)
	public List<CoverageRuleEntity> addCoverageRulesBulk(
	        @PathVariable String planCode,
	        @RequestBody CreateCoverageRuleBulkRequest request) throws ConflictException, NotFoundException {

	    Long payerOrgId = SecurityContextUtil.getCurrentOrgId();
	    return payerPlanService.addCoverageRulesBulk(payerOrgId, planCode, request);
	}


}
