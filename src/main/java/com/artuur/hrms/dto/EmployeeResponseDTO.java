package com.artuur.hrms.dto;

import com.artuur.hrms.entities.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record EmployeeResponseDTO(
        UUID employeeId,
        String nome,
        String cpf,
        BigDecimal baseSalary,
        BigDecimal healthPlan,
        LocalDate admissionDate,
        String departmentName,
        String positionName,
        String statusName,
        String userEmail,
        String scaleName,
        LocalDate scaleStartDate
) {

    public EmployeeResponseDTO(Employee employee) {
        this(
                employee.getEmployeeId(),
                employee.getNome(),
                employee.getCpf(),
                employee.getBaseSalary(),
                employee.getHealthPlan(),
                employee.getAdmissionDate(),
                employee.getDepartment() != null ? employee.getDepartment().getName() : null,
                employee.getPosition() != null ? employee.getPosition().getTitle() : null,
                employee.getStatus() != null ? employee.getStatus().getName() : null,
                employee.getUser() != null ? employee.getUser().getEmail() : null,
                employee.getScale() != null ? employee.getScale().getName() : "Não definida",
                employee.getScaleStartDate()
        );
    }
}