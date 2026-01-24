package com.artuur.hrms.services;

import com.artuur.hrms.dto.CreatePayrollDTO;
import com.artuur.hrms.dto.PayrollResponseDTO;
import com.artuur.hrms.entities.Payroll;
import com.artuur.hrms.repository.EmployeeRepository;
import com.artuur.hrms.repository.PayrollRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final EmployeeRepository employeeRepository;
    private static final BigDecimal TETO_INSS_2026 = new BigDecimal("8475.55");

    public PayrollService(PayrollRepository payrollRepository, EmployeeRepository employeeRepository) {
        this.payrollRepository = payrollRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public List<Payroll> findAllByEmployeeId(UUID id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));

        return payrollRepository.findAllByEmployee_EmployeeIdOrderByReferenceMonthDesc(employee.getEmployeeId());
    }

    @Transactional
    public PayrollResponseDTO newPayroll(CreatePayrollDTO dto, LocalDate referenceMonth) {
        var employee = employeeRepository.findById(dto.employeeId())
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));

        BigDecimal employeeBaseSalary = employee.getBaseSalary();

        BigDecimal overtime = BigDecimal.ZERO;
        BigDecimal dsr = calculateDsr(overtime);

        BigDecimal inss = calculateInss(employeeBaseSalary);
        BigDecimal irrf = calculateIrrf(employeeBaseSalary.subtract(inss));

        BigDecimal healthPlan = employee.getHealthPlan();

        BigDecimal totalEarnings = employeeBaseSalary.add(dsr).add(overtime);
        BigDecimal totalDiscounts = inss.add(irrf).add(healthPlan);
        BigDecimal netSalary = totalEarnings.subtract(totalDiscounts);

        Payroll payroll = Payroll.builder()
                .employee(employee)
                .referenceMonth(referenceMonth.withDayOfMonth(1))
                .baseSalary(employeeBaseSalary)
                .overtimeValue(overtime)
                .dsrValue(dsr)
                .irrfValue(irrf)
                .inssValue(inss)
                .healthPlanValue(healthPlan)
                .totalEarnings(totalEarnings)
                .totalDiscounts(totalDiscounts)
                .netSalary(netSalary)
                .generatedAt(LocalDateTime.now())
                .build();

        return new PayrollResponseDTO(payrollRepository.save(payroll));

    }

    private BigDecimal calculateIrrf(BigDecimal baseCalculation) {

        if (baseCalculation.compareTo(new BigDecimal("2428.80")) <= 0) {
            return BigDecimal.ZERO; // Isento
        } else if (baseCalculation.compareTo(new BigDecimal("2826.65")) <= 0) {
            return baseCalculation.multiply(new BigDecimal("0.075")).subtract(new BigDecimal("182.16"));
        } else if (baseCalculation.compareTo(new BigDecimal("3751.05")) <= 0) {
            return baseCalculation.multiply(new BigDecimal("0.15")).subtract(new BigDecimal("394.16"));
        } else if (baseCalculation.compareTo(new BigDecimal("4664.68")) <= 0) {
            return baseCalculation.multiply(new BigDecimal("0.225")).subtract(new BigDecimal("675.49"));
        } else {
            return baseCalculation.multiply(new BigDecimal("0.275")).subtract(new BigDecimal("908.73"));
        }
    }

    private BigDecimal calculateInss(BigDecimal salary) {
        BigDecimal baseCalculo = salary.compareTo(TETO_INSS_2026) > 0 ? TETO_INSS_2026 : salary;

        BigDecimal totalInss = BigDecimal.ZERO;

        // Faixa 1: Até R$ 1.621,00 (7,5%)
        BigDecimal limiteFaixa1 = new BigDecimal("1621.00");
        if (baseCalculo.compareTo(limiteFaixa1) > 0) {
            totalInss = totalInss.add(limiteFaixa1.multiply(new BigDecimal("0.075")));
        } else {
            return baseCalculo.multiply(new BigDecimal("0.075")).setScale(2, RoundingMode.HALF_UP);
        }

        // Faixa 2: De 1.621,01 até 2.902,84 (9%)
        BigDecimal limiteFaixa2 = new BigDecimal("2902.84");
        if (baseCalculo.compareTo(limiteFaixa2) > 0) {
            totalInss = totalInss.add(limiteFaixa2.subtract(limiteFaixa1).multiply(new BigDecimal("0.09")));
        } else {
            totalInss = totalInss.add(baseCalculo.subtract(limiteFaixa1).multiply(new BigDecimal("0.09")));
            return totalInss.setScale(2, RoundingMode.HALF_UP);
        }

        // Faixa 3: De 2.902,85 até 4.354,27 (12%)
        BigDecimal limiteFaixa3 = new BigDecimal("4354.27");
        if (baseCalculo.compareTo(limiteFaixa3) > 0) {
            totalInss = totalInss.add(limiteFaixa3.subtract(limiteFaixa2).multiply(new BigDecimal("0.12")));
        } else {
            totalInss = totalInss.add(baseCalculo.subtract(limiteFaixa2).multiply(new BigDecimal("0.12")));
            return totalInss.setScale(2, RoundingMode.HALF_UP);
        }

        // Faixa 4: De 4.354,28 até o Teto (14%)
        totalInss = totalInss.add(baseCalculo.subtract(limiteFaixa3).multiply(new BigDecimal("0.14")));

        return totalInss.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDsr(BigDecimal overtime) {
        if (overtime.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;

        // Proporção aproximada de 1/6 (um dia de folga para 6 trabalhados)
        return overtime.divide(new BigDecimal("6"), 2, RoundingMode.HALF_UP);
    }

}
