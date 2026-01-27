package com.ct.usecase.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ct.usecase.demo.entity.CoverageRuleEntity;
import com.ct.usecase.demo.enums.ServiceCode;

public interface CoverageRuleRepository extends JpaRepository<CoverageRuleEntity, Long> {

    Optional<CoverageRuleEntity> findByPlan_IdAndServiceCode(
            Long planId,
            ServiceCode serviceCode
    );

    boolean existsByPlan_IdAndServiceCode(
            Long planId,
            ServiceCode serviceCode
    );

    List<CoverageRuleEntity> findByPlan_Id(Long planId);
}
