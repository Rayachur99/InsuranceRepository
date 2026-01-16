package com.ct.usecase.demo.dto;

import java.time.LocalDate;

public record EligibilityCheckRequest(
        String memberExternalId,
        String serviceCode,
        LocalDate requestDate
) {}

