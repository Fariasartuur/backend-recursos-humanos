package com.artuur.hrms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record UpdateEmployeeDTO(
        String nome,
        BigDecimal baseSalary,
        BigDecimal healthPlan,
        UUID departmentId,
        UUID positionId,
        Long statusId,
        UUID scaleId,
        LocalDate scaleStartDate
) {
}
