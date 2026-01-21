package com.artuur.hrms.dto;

import java.math.BigDecimal;

public record UpdateEmployeeDTO(
        String nome,
        BigDecimal baseSalary,
        Long departmentId,
        Long positionId,
        Long statusId
) {
}
