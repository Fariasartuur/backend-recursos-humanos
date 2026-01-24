package com.artuur.hrms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateEmployeeDTO(
        String nome,
        BigDecimal baseSalary,
        BigDecimal healthPlan,
        Long departmentId,
        Long positionId,
        Long statusId,
        Long scaleId,
        LocalDate scaleStartDate
) {
}
