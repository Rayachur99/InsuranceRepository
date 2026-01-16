package com.ct.usecase.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ct.usecase.demo.entity.InsurancePlanEntity;

public interface InsurancePlanRepository
        extends JpaRepository<InsurancePlanEntity, Long> {

    List<InsurancePlanEntity> findByPayerOrganizationId(Long orgId);
}

