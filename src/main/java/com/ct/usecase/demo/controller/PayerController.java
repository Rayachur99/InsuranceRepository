package com.ct.usecase.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ct.usecase.demo.dto.CreateCoverageRuleBulkRequest;
import com.ct.usecase.demo.dto.CreatePlanRequest;
import com.ct.usecase.demo.dto.UpdateCoverageRuleRequest;
import com.ct.usecase.demo.entity.CoverageRuleEntity;
import com.ct.usecase.demo.entity.InsurancePlanEntity;
import com.ct.usecase.demo.enums.ServiceCode;
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

	//post api
	@PostMapping("/plans")
	public InsurancePlanEntity createPlan(@RequestBody CreatePlanRequest request) {
		Long payerOrgId = SecurityContextUtil.getCurrentOrgId();

		return payerPlanService.createPlan(payerOrgId, request.planCode(), request.planName(), request.validFrom(),
				request.validTo());
	}

	@PutMapping("/plans/{planCode}/rules/{serviceCode}")
	public CoverageRuleEntity updateCoverageRule(
	        @PathVariable String planCode,
	        @PathVariable ServiceCode serviceCode,
	        @RequestBody UpdateCoverageRuleRequest request) {

	    Long payerOrgId = SecurityContextUtil.getCurrentOrgId();
	    return payerPlanService.updateCoverageRule(
	            payerOrgId, planCode, serviceCode, request
	    );
	}

	
	@PostMapping("/plans/{planCode}/rules/bulk")
	@ResponseStatus(HttpStatus.CREATED)
	public List<CoverageRuleEntity> addCoverageRulesBulk(
	        @PathVariable String planCode,
	        @RequestBody CreateCoverageRuleBulkRequest request) throws ConflictException {

	    Long payerOrgId = SecurityContextUtil.getCurrentOrgId();
	    return payerPlanService.addCoverageRulesBulk(payerOrgId, planCode, request);
	}


}
