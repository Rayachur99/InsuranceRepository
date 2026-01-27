package com.ct.usecase.demo.dto;

import java.time.LocalDate;

public record EnrollMemberRequest(
        String memberExternalId,
        String fullName,
        Integer age,
        String planCode,
        LocalDate coverageStartDate,
        LocalDate coverageEndDate
) {}

