package com.ct.usecase.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "coverage_rules")
public class CoverageRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceCode;

    private boolean covered;
    private boolean priorAuthRequired;

    private Integer minAge;
    private Integer maxAge;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private InsurancePlanEntity plan;

	public CoverageRuleEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public boolean isCovered() {
		return covered;
	}

	public void setCovered(boolean covered) {
		this.covered = covered;
	}

	public boolean isPriorAuthRequired() {
		return priorAuthRequired;
	}

	public void setPriorAuthRequired(boolean priorAuthRequired) {
		this.priorAuthRequired = priorAuthRequired;
	}

	public Integer getMinAge() {
		return minAge;
	}

	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}

	public Integer getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	public InsurancePlanEntity getPlan() {
		return plan;
	}

	public void setPlan(InsurancePlanEntity plan) {
		this.plan = plan;
	}

	@Override
	public String toString() {
		return "CoverageRuleEntity [id=" + id + ", serviceCode=" + serviceCode + ", covered=" + covered
				+ ", priorAuthRequired=" + priorAuthRequired + ", minAge=" + minAge + ", maxAge=" + maxAge + ", plan="
				+ plan + "]";
	}
}

