package com.ct.usecase.demo.entity;

import com.ct.usecase.demo.ServiceCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "coverage_rules")
public class CoverageRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceCode serviceCode;

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

	public ServiceCode getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(ServiceCode serviceCode) {
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

