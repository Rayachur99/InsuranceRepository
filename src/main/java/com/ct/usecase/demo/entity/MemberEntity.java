package com.ct.usecase.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "members")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberExternalId; // e.g. policy number
    private String fullName;
    private Integer age;

    private LocalDate coverageStartDate;
    private LocalDate coverageEndDate;

    @ManyToOne
    @JoinColumn(name = "payer_org_id", nullable = false)
    private OrganizationEntity payerOrganization;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private InsurancePlanEntity plan;

	public MemberEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberExternalId() {
		return memberExternalId;
	}

	public void setMemberExternalId(String memberExternalId) {
		this.memberExternalId = memberExternalId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public LocalDate getCoverageStartDate() {
		return coverageStartDate;
	}

	public void setCoverageStartDate(LocalDate coverageStartDate) {
		this.coverageStartDate = coverageStartDate;
	}

	public LocalDate getCoverageEndDate() {
		return coverageEndDate;
	}

	public void setCoverageEndDate(LocalDate coverageEndDate) {
		this.coverageEndDate = coverageEndDate;
	}

	public OrganizationEntity getPayerOrganization() {
		return payerOrganization;
	}

	public void setPayerOrganization(OrganizationEntity payerOrganization) {
		this.payerOrganization = payerOrganization;
	}

	public InsurancePlanEntity getPlan() {
		return plan;
	}

	public void setPlan(InsurancePlanEntity plan) {
		this.plan = plan;
	}

	@Override
	public String toString() {
		return "MemberEntity [id=" + id + ", memberExternalId=" + memberExternalId + ", fullName=" + fullName + ", age="
				+ age + ", coverageStartDate=" + coverageStartDate + ", coverageEndDate=" + coverageEndDate
				+ ", payerOrganization=" + payerOrganization + ", plan=" + plan + "]";
	}


}

