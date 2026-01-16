package com.ct.usecase.demo.entity;

import com.ct.usecase.demo.util.OrganizationType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "organizations")
public class OrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private OrganizationType type; // PROVIDER, PAYER

	public OrganizationEntity(String name, OrganizationType type) {
		super();
		this.name = name;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OrganizationType getType() {
		return type;
	}

	public void setType(OrganizationType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "OrganizationEntity [id=" + id + ", name=" + name + ", type=" + type + "]";
	}

	public OrganizationEntity() {
		super();
	}

	public OrganizationEntity(Long id) {
		super();
		this.id = id;
	}
}

