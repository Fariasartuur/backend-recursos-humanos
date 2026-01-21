package com.artuur.hrms.repository;

import com.artuur.hrms.entities.Department;
import com.artuur.hrms.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
