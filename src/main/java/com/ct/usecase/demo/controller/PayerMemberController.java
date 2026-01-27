package com.ct.usecase.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.usecase.demo.dto.EnrollMemberRequest;
import com.ct.usecase.demo.entity.MemberEntity;
import com.ct.usecase.demo.service.MemberEnrollmentService;
import com.ct.usecase.demo.util.SecurityContextUtil;

@RestController
@RequestMapping("/payer/members")
public class PayerMemberController {

	private final MemberEnrollmentService enrollmentService;

	public PayerMemberController(MemberEnrollmentService enrollmentService) {
		this.enrollmentService = enrollmentService;
	}

	@PostMapping
	public MemberEntity enrollMember(@RequestBody EnrollMemberRequest request) {
		Long payerOrgId = SecurityContextUtil.getCurrentOrgId();
		return enrollmentService.enrollMember(payerOrgId, request);
	}
	
	
}
