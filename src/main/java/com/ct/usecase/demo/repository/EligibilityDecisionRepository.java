package com.ct.usecase.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ct.usecase.demo.entity.EligibilityDecisionEntity;

public interface EligibilityDecisionRepository
        extends JpaRepository<EligibilityDecisionEntity, Long> {
}
