package com.ct.usecase.demo.service;

import org.springframework.stereotype.Service;

import com.ct.usecase.demo.dto.CreateCoverageRuleRequest;
import com.ct.usecase.demo.entity.CoverageRuleEntity;
import com.ct.usecase.demo.entity.InsurancePlanEntity;
import com.ct.usecase.demo.entity.OrganizationEntity;
import com.ct.usecase.demo.repository.CoverageRuleRepository;
import com.ct.usecase.demo.repository.InsurancePlanRepository;
import com.ct.usecase.demo.repository.OrganizationRepository;

@Service
public class PayerPlanService {

	private final InsurancePlanRepository planRepository;
	private final CoverageRuleRepository ruleRepository;
	private final OrganizationRepository organizationRepository;

	public PayerPlanService(InsurancePlanRepository planRepository, CoverageRuleRepository ruleRepository,
			OrganizationRepository organizationRepository) {
		this.planRepository = planRepository;
		this.ruleRepository = ruleRepository;
		this.organizationRepository = organizationRepository;
	}

	public InsurancePlanEntity createPlan(Long payerOrgId, String planCode, String planName, java.time.LocalDate from,
			java.time.LocalDate to) {
		OrganizationEntity payerOrg = organizationRepository.findById(payerOrgId).orElseThrow();

		InsurancePlanEntity plan = new InsurancePlanEntity();
		plan.setPlanCode(planCode);
		plan.setPlanName(planName);
		plan.setValidFrom(from);
		plan.setValidTo(to);
		plan.setPayerOrganization(payerOrg);

		return planRepository.save(plan);
	}

	public CoverageRuleEntity addCoverageRule(Long payerOrgId, Long planId, CreateCoverageRuleRequest request) {
		InsurancePlanEntity plan = planRepository.findById(planId).orElseThrow();

		// 🔐 ORG-LEVEL RBAC (CRITICAL)
		if (!plan.getPayerOrganization().getId().equals(payerOrgId)) {
			throw new SecurityException("Cannot modify another payer's plan");
		}

		CoverageRuleEntity rule = new CoverageRuleEntity();
		rule.setServiceCode(request.serviceCode());
		rule.setCovered(request.covered());
		rule.setPriorAuthRequired(request.priorAuthRequired());
		rule.setMinAge(request.minAge());
		rule.setMaxAge(request.maxAge());
		rule.setPlan(plan);

		return ruleRepository.save(rule);
	}
}
