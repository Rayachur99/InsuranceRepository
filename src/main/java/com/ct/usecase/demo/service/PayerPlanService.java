package com.ct.usecase.demo.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ct.usecase.demo.dto.CreateCoverageRuleBulkRequest;
import com.ct.usecase.demo.dto.CreateCoverageRuleRequest;
import com.ct.usecase.demo.dto.UpdateCoverageRuleRequest;
import com.ct.usecase.demo.entity.CoverageRuleEntity;
import com.ct.usecase.demo.entity.InsurancePlanEntity;
import com.ct.usecase.demo.entity.OrganizationEntity;
import com.ct.usecase.demo.enums.ServiceCode;
import com.ct.usecase.demo.exception.ConflictException;
import com.ct.usecase.demo.exception.ForbiddenException;
import com.ct.usecase.demo.exception.ResourceNotFoundException;
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

	public CoverageRuleEntity addCoverageRule(
	        Long payerOrgId,
	        String planCode,
	        CreateCoverageRuleRequest request) throws ConflictException {

	    InsurancePlanEntity plan = planRepository
	            .findByPlanCodeAndPayerOrganization_Id(planCode, payerOrgId)
	            .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));

	    // ORG-level safety (defensive, still fine)
	    if (!plan.getPayerOrganization().getId().equals(payerOrgId)) {
	        throw new ForbiddenException("Cannot modify another payer's plan");
	    }

	    // ✅ DUPLICATE COVERAGE CHECK
	    boolean alreadyExists = ruleRepository.existsByPlan_IdAndServiceCode(
	            plan.getId(),
	            request.serviceCode()
	    );

	    if (alreadyExists) {
	        throw new ConflictException(
	                "Coverage rule already exists for service: " + request.serviceCode()
	        );
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

	
	@Transactional
	public List<CoverageRuleEntity> addCoverageRulesBulk(
	        Long payerOrgId,
	        String planCode,
	        CreateCoverageRuleBulkRequest bulkRequest) throws ConflictException {

	    InsurancePlanEntity plan = planRepository
	            .findByPlanCodeAndPayerOrganization_Id(planCode, payerOrgId)
	            .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));

	    Set<ServiceCode> existingServices =
	            ruleRepository.findByPlan_Id(plan.getId()).stream()
	                    .map(CoverageRuleEntity::getServiceCode)
	                    .collect(Collectors.toSet());

	    for (CreateCoverageRuleRequest req : bulkRequest.rules()) {
	        if (existingServices.contains(req.serviceCode())) {
	            throw new ConflictException(
	                    "Coverage already exists for service: " + req.serviceCode()
	            );
	        }
	    }

	    List<CoverageRuleEntity> rulesToSave = bulkRequest.rules().stream()
	            .map(req -> {
	                CoverageRuleEntity rule = new CoverageRuleEntity();
	                rule.setServiceCode(req.serviceCode());
	                rule.setCovered(req.covered());
	                rule.setPriorAuthRequired(req.priorAuthRequired());
	                rule.setMinAge(req.minAge());
	                rule.setMaxAge(req.maxAge());
	                rule.setPlan(plan);
	                return rule;
	            })
	            .toList();

	    return ruleRepository.saveAll(rulesToSave);
	}
	
	@Transactional
	public CoverageRuleEntity updateCoverageRule(
	        Long payerOrgId,
	        String planCode,
	        ServiceCode serviceCode,
	        UpdateCoverageRuleRequest request) {

	    InsurancePlanEntity plan = planRepository
	            .findByPlanCodeAndPayerOrganization_Id(planCode, payerOrgId)
	            .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));

	    CoverageRuleEntity rule = ruleRepository
	            .findByPlan_IdAndServiceCode(plan.getId(), serviceCode)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Coverage rule not found for service: " + serviceCode
	                    )
	            );

	    // Update allowed fields only
	    rule.setCovered(request.covered());
	    rule.setPriorAuthRequired(request.priorAuthRequired());
	    rule.setMinAge(request.minAge());
	    rule.setMaxAge(request.maxAge());

	    return ruleRepository.save(rule);
	}



}
