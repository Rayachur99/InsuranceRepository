package com.ct.usecase.demo.entity;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.ct.usecase.demo.ServiceCode;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "eligibility_requests")
public class EligibilityRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private ServiceCode serviceCode;
    private LocalDate requestDate;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @ManyToOne
    @JoinColumn(name = "provider_org_id", nullable = false)
    private OrganizationEntity providerOrganization;

    private OffsetDateTime createdAt = OffsetDateTime.now();

	public EligibilityRequestEntity() {
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

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public MemberEntity getMember() {
		return member;
	}

	public void setMember(MemberEntity member) {
		this.member = member;
	}

	public OrganizationEntity getProviderOrganization() {
		return providerOrganization;
	}

	public void setProviderOrganization(OrganizationEntity providerOrganization) {
		this.providerOrganization = providerOrganization;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "EligibilityRequestEntity [id=" + id + ", serviceCode=" + serviceCode + ", requestDate=" + requestDate
				+ ", member=" + member + ", providerOrganization=" + providerOrganization + ", createdAt=" + createdAt
				+ "]";
	}

}
