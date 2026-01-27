package com.ct.usecase.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ct.usecase.demo.entity.InsurancePlanEntity;

public interface InsurancePlanRepository
        extends JpaRepository<InsurancePlanEntity, Long> {

    List<InsurancePlanEntity> findByPayerOrganizationId(Long orgId);
    
    Optional<InsurancePlanEntity> findByPlanCodeAndPayerOrganization_Id(
            String planCode,
            Long payerOrgId
    );
}

