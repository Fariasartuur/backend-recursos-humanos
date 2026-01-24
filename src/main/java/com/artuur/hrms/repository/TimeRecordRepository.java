package com.artuur.hrms.repository;

import com.artuur.hrms.entities.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TimeRecordRepository extends JpaRepository<TimeRecord, UUID> {

    List<TimeRecord> findAllByEmployee_EmployeeId(UUID employeeEmployeeId);
    Optional<TimeRecord> findByEmployee_EmployeeIdAndDate(UUID id, LocalDate date);
}
