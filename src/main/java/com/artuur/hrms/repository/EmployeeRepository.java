package com.artuur.hrms.repository;

import com.artuur.hrms.entities.Employee;
import com.artuur.hrms.entities.Payroll;
import com.artuur.hrms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByUser_UserId(UUID userId);
    Optional<Employee> findByCpf(String cpf);
}
