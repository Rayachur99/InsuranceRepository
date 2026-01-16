package com.ct.usecase.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ct.usecase.demo.entity.EligibilityRequestEntity;

public interface EligibilityRequestRepository
        extends JpaRepository<EligibilityRequestEntity, Long> {
}
