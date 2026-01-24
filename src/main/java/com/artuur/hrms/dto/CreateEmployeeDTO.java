package com.artuur.hrms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateEmployeeDTO(
        String nome,
        String cpf,
        BigDecimal baseSalary,
        BigDecimal healthPlan,
        LocalDate admissionDate,
        UUID userId,
        Long departmentId,
        Long positionId,
        Long statusId,
        Long scaleId,
        LocalDate scaleStartDate
) {
}
