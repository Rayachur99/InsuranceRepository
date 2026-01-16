package com.ct.usecase.demo.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "eligibility_decisions")
public class EligibilityDecisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "request_id", nullable = false)
    private EligibilityRequestEntity request;

    private boolean eligible;
    private boolean priorAuthRequired;
    private String reasonCode;

    private OffsetDateTime decidedAt = OffsetDateTime.now();

	public EligibilityDecisionEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EligibilityRequestEntity getRequest() {
		return request;
	}

	public void setRequest(EligibilityRequestEntity request) {
		this.request = request;
	}

	public boolean isEligible() {
		return eligible;
	}

	public void setEligible(boolean eligible) {
		this.eligible = eligible;
	}

	public boolean isPriorAuthRequired() {
		return priorAuthRequired;
	}

	public void setPriorAuthRequired(boolean priorAuthRequired) {
		this.priorAuthRequired = priorAuthRequired;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public OffsetDateTime getDecidedAt() {
		return decidedAt;
	}

	public void setDecidedAt(OffsetDateTime decidedAt) {
		this.decidedAt = decidedAt;
	}

	@Override
	public String toString() {
		return "EligibilityDecisionEntity [id=" + id + ", request=" + request + ", eligible=" + eligible
				+ ", priorAuthRequired=" + priorAuthRequired + ", reasonCode=" + reasonCode + ", decidedAt=" + decidedAt
				+ "]";
	}

    

}

