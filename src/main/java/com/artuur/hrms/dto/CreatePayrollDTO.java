package com.artuur.hrms.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CreatePayrollDTO(
        UUID employeeId,
        LocalDate referenceMonth
) {
}
