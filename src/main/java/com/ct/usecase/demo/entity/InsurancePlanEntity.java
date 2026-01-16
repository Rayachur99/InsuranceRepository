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
@Table(name = "insurance_plans")
public class InsurancePlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String planCode;
    private String planName;

    private LocalDate validFrom;
    private LocalDate validTo;

    @ManyToOne
    @JoinColumn(name = "payer_org_id", nullable = false)
    private OrganizationEntity payerOrganization;

	public InsurancePlanEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public LocalDate getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	public LocalDate getValidTo() {
		return validTo;
	}

	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}

	public OrganizationEntity getPayerOrganization() {
		return payerOrganization;
	}

	public void setPayerOrganization(OrganizationEntity payerOrganization) {
		this.payerOrganization = payerOrganization;
	}

	@Override
	public String toString() {
		return "InsurancePlanEntity [id=" + id + ", planCode=" + planCode + ", planName=" + planName + ", validFrom="
				+ validFrom + ", validTo=" + validTo + ", payerOrganization=" + payerOrganization + "]";
	}
}
