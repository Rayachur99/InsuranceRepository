package com.ct.usecase.demo.entity;

import com.ct.usecase.demo.util.UserRole;

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
@Table(name = "users")
public class UserEntity {

    public UserEntity() {
		super();
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", username=" + username + ", passwordHash=" + passwordHash + ", role=" + role
				+ ", organization=" + organization + "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public OrganizationEntity getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
	}

	public UserEntity(String username, String passwordHash, UserRole role, OrganizationEntity organization) {
		super();
		this.username = username;
		this.passwordHash = passwordHash;
		this.role = role;
		this.organization = organization;
	}

	public UserEntity(Long id, String username, String passwordHash, UserRole role, OrganizationEntity organization) {
		super();
		this.id = id;
		this.username = username;
		this.passwordHash = passwordHash;
		this.role = role;
		this.organization = organization;
	}

	@ManyToOne
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;
}

