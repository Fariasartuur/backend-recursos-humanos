package com.artuur.hrms.repository;

import com.artuur.hrms.entities.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, UUID> {

    List<Payroll> findAllByEmployee_EmployeeIdOrderByReferenceMonthDesc(UUID id);
}
