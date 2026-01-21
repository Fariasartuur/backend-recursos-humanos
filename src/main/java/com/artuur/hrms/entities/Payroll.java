package com.artuur.hrms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_payrolls")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payroll_id")
    private UUID payrollId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = false)
    private LocalDate referenceMonth;

    @Column(nullable = false)
    private BigDecimal baseSalary;

    @Column(nullable = false)
    private BigDecimal overtimeValue;

    @Column(nullable = false)
    private BigDecimal dsrValue;

    @Column(nullable = false)
    private BigDecimal inssValue;

    @Column(nullable = false)
    private BigDecimal irrfValue;

    @Column(nullable = false)
    private BigDecimal healthPlanValue;

    @Column(nullable = false)
    private BigDecimal totalEarnings;

    @Column(nullable = false)
    private BigDecimal totalDiscounts;

    @Column(nullable = false)
    private BigDecimal netSalary;

    @Column(nullable = false)
    private LocalDateTime generatedAt;


}
