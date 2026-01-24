package com.artuur.hrms.repository;

import com.artuur.hrms.entities.Employee;
import com.artuur.hrms.entities.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByUser_UserId(UUID userId);
}
