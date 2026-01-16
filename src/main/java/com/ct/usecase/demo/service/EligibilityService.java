package com.ct.usecase.demo.service;

import org.springframework.stereotype.Service;

import com.ct.usecase.demo.dto.EligibilityCheckRequest;
import com.ct.usecase.demo.dto.EligibilityCheckResponse;
import com.ct.usecase.demo.dto.EligibilityDecisionModel;
import com.ct.usecase.demo.entity.CoverageRuleEntity;
import com.ct.usecase.demo.entity.EligibilityDecisionEntity;
import com.ct.usecase.demo.entity.EligibilityRequestEntity;
import com.ct.usecase.demo.entity.MemberEntity;
import com.ct.usecase.demo.entity.OrganizationEntity;
import com.ct.usecase.demo.repository.CoverageRuleRepository;
import com.ct.usecase.demo.repository.EligibilityDecisionRepository;
import com.ct.usecase.demo.repository.EligibilityRequestRepository;
import com.ct.usecase.demo.repository.MemberRepository;
import com.ct.usecase.demo.util.EligibilityEngine;

@Service
public class EligibilityService {

	private final MemberRepository memberRepository;
	private final CoverageRuleRepository ruleRepository;
	private final EligibilityRequestRepository requestRepository;
	private final EligibilityDecisionRepository decisionRepository;

	public EligibilityService(MemberRepository memberRepository, CoverageRuleRepository ruleRepository,
			EligibilityRequestRepository requestRepository, EligibilityDecisionRepository decisionRepository) {
		this.memberRepository = memberRepository;
		this.ruleRepository = ruleRepository;
		this.requestRepository = requestRepository;
		this.decisionRepository = decisionRepository;
	}

	public EligibilityCheckResponse checkEligibility(Long providerOrgId, EligibilityCheckRequest request) {
		MemberEntity member = memberRepository.findByMemberExternalId(request.memberExternalId())
				.orElseThrow(() -> new RuntimeException("Member not found"));

		CoverageRuleEntity rule = ruleRepository.findByPlanId(member.getPlan().getId()).stream()
				.filter(r -> r.getServiceCode().equals(request.serviceCode())).findFirst().orElse(null);

		EligibilityRequestEntity reqEntity = new EligibilityRequestEntity();
		reqEntity.setMember(member);
		reqEntity.setServiceCode(request.serviceCode());
		reqEntity.setRequestDate(request.requestDate());
		reqEntity.setProviderOrganization(new OrganizationEntity(providerOrgId));
		requestRepository.save(reqEntity);

		EligibilityEngine engine = new EligibilityEngine();
		EligibilityDecisionModel model = engine.evaluate(member, rule, request.requestDate());

		EligibilityDecisionEntity decision = new EligibilityDecisionEntity();
		decision.setRequest(reqEntity);
		decision.setEligible(model.eligible());
		decision.setPriorAuthRequired(model.priorAuthRequired());
		decision.setReasonCode(model.reasonCode());
		decisionRepository.save(decision);

		return new EligibilityCheckResponse(model.eligible(), model.priorAuthRequired(), model.reasonCode());
	}
}
