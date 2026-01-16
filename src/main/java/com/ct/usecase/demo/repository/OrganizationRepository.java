package com.ct.usecase.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ct.usecase.demo.entity.OrganizationEntity;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {
}

