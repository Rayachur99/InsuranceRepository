package com.ct.usecase.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ct.usecase.demo.entity.CoverageRuleEntity;

public interface CoverageRuleRepository
        extends JpaRepository<CoverageRuleEntity, Long> {

    List<CoverageRuleEntity> findByPlanId(Long planId);
}
