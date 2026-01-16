package com.ct.usecase.demo.dto;

import java.time.LocalDate;

public record CreatePlanRequest(
        String planCode,
        String planName,
        LocalDate validFrom,
        LocalDate validTo
) {}

