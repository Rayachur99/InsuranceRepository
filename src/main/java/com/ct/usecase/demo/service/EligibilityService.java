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
import com.ct.usecase.demo.exception.ResourceNotFoundException;
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
    private final EligibilityEngine eligibilityEngine;

    public EligibilityService(
            MemberRepository memberRepository,
            CoverageRuleRepository ruleRepository,
            EligibilityRequestRepository requestRepository,
            EligibilityDecisionRepository decisionRepository,
            EligibilityEngine eligibilityEngine
    ) {
        this.memberRepository = memberRepository;
        this.ruleRepository = ruleRepository;
        this.requestRepository = requestRepository;
        this.decisionRepository = decisionRepository;
        this.eligibilityEngine = eligibilityEngine;
    }

    public EligibilityCheckResponse checkEligibility(
            Long providerOrgId,
            EligibilityCheckRequest request
    ) {

        MemberEntity member = memberRepository
                .findByMemberExternalId(request.memberExternalId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found")
                );

        CoverageRuleEntity rule = ruleRepository
                .findById(member.getPlan().getId())
                .stream()
                .filter(r -> r.getServiceCode().equals(request.serviceCode()))
                .findFirst()
                .orElse(null);

        // Persist request
        EligibilityRequestEntity reqEntity = new EligibilityRequestEntity();
        reqEntity.setMember(member);
        reqEntity.setServiceCode(request.serviceCode());
        reqEntity.setRequestDate(request.requestDate());
        reqEntity.setProviderOrganization(new OrganizationEntity(providerOrgId));
        requestRepository.save(reqEntity);

        // Domain evaluation
        EligibilityDecisionModel decisionModel =
                eligibilityEngine.evaluate(member, rule, request.requestDate());

        // Persist decision
        EligibilityDecisionEntity decisionEntity = new EligibilityDecisionEntity();
        decisionEntity.setRequest(reqEntity);
        decisionEntity.setEligible(decisionModel.eligible());
        decisionEntity.setPriorAuthRequired(decisionModel.priorAuthRequired());
        decisionEntity.setReasonCode(decisionModel.outcome().name());
        decisionRepository.save(decisionEntity);

        // API response
        return new EligibilityCheckResponse(
                decisionModel.eligible(),
                decisionModel.priorAuthRequired(),
                decisionModel.outcome().name()
        );
    }
}
