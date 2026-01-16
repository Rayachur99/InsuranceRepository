package com.ct.usecase.demo.service;

import org.springframework.stereotype.Service;

import com.ct.usecase.demo.dto.EnrollMemberRequest;
import com.ct.usecase.demo.entity.InsurancePlanEntity;
import com.ct.usecase.demo.entity.MemberEntity;
import com.ct.usecase.demo.repository.InsurancePlanRepository;
import com.ct.usecase.demo.repository.MemberRepository;

@Service
public class MemberEnrollmentService {

    private final MemberRepository memberRepository;
    private final InsurancePlanRepository planRepository;

    public MemberEnrollmentService(
            MemberRepository memberRepository,
            InsurancePlanRepository planRepository
    ) {
        this.memberRepository = memberRepository;
        this.planRepository = planRepository;
    }

    public MemberEntity enrollMember(
            Long payerOrgId,
            EnrollMemberRequest request
    ) {
        InsurancePlanEntity plan =
                planRepository.findById(request.planId())
                        .orElseThrow(() -> new RuntimeException("Plan not found"));

        // 🔐 ORG-LEVEL RBAC (CRITICAL)
        if (!plan.getPayerOrganization().getId().equals(payerOrgId)) {
            throw new SecurityException("Cannot enroll member into another payer's plan");
        }

        MemberEntity member = new MemberEntity();
        member.setMemberExternalId(request.memberExternalId());
        member.setFullName(request.fullName());
        member.setAge(request.age());
        member.setCoverageStartDate(request.coverageStartDate());
        member.setCoverageEndDate(request.coverageEndDate());
        member.setPlan(plan);
        member.setPayerOrganization(plan.getPayerOrganization());

        return memberRepository.save(member);
    }
}

