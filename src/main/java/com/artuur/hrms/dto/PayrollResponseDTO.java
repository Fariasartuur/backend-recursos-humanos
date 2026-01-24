package com.artuur.hrms.dto;

import com.artuur.hrms.entities.Payroll;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PayrollResponseDTO(
        UUID payrollId,
        String employeeName,
        String referenceMonth,
        BigDecimal baseSalary,
        BigDecimal overtimeValue,
        BigDecimal dsrValue,
        BigDecimal inssValue,
        BigDecimal irrfValue,
        BigDecimal healthPlanValue,
        BigDecimal totalEarnings,
        BigDecimal totalDiscounts,
        BigDecimal netSalary,
        LocalDateTime generatedAt
) {
    public PayrollResponseDTO(Payroll payroll) {
        this(
                payroll.getPayrollId(),
                payroll.getEmployee().getNome(),
                payroll.getReferenceMonth().getMonthValue() + "/" + payroll.getReferenceMonth().getYear(),
                payroll.getBaseSalary(),
                payroll.getOvertimeValue(),
                payroll.getDsrValue(),
                payroll.getInssValue(),
                payroll.getIrrfValue(),
                payroll.getHealthPlanValue(),
                payroll.getTotalEarnings(),
                payroll.getTotalDiscounts(),
                payroll.getNetSalary(),
                payroll.getGeneratedAt()
        );
    }
}
