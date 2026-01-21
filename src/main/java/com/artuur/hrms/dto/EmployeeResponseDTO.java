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
        LocalDate admissionDate,
        String departmentName,
        String positionName,
        String statusName,
        String userEmail
) {

    public EmployeeResponseDTO(Employee employee) {
        this(
                employee.getEmployeeId(),
                employee.getNome(),
                employee.getCpf(),
                employee.getBaseSalary(),
                employee.getAdmissionDate(),
                employee.getDepartment().getName(),
                employee.getPosition().getTitle(),
                employee.getStatus().getName(),
                employee.getUser() != null ? employee.getUser().getEmail() : null
        );
    }
}