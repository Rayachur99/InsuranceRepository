package com.ct.usecase.demo.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.usecase.demo.dto.CreateCoverageRuleRequest;
import com.ct.usecase.demo.dto.CreatePlanRequest;
import com.ct.usecase.demo.entity.CoverageRuleEntity;
import com.ct.usecase.demo.entity.InsurancePlanEntity;
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

	@PostMapping("/plans/{planId}/rules")
	public CoverageRuleEntity addCoverageRule(@PathVariable Long planId,
			@RequestBody CreateCoverageRuleRequest request) {
		Long payerOrgId = SecurityContextUtil.getCurrentOrgId();
		return payerPlanService.addCoverageRule(payerOrgId, planId, request);
	}
}
