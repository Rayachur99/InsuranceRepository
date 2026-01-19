package com.ct.usecase.demo.dto;

import java.time.LocalDate;

import com.ct.usecase.demo.ServiceCode;

public record EligibilityCheckRequest(
        String memberExternalId,
        ServiceCode serviceCode,
        LocalDate requestDate
) {}

