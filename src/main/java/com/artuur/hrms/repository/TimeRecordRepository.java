package com.artuur.hrms.repository;

import com.artuur.hrms.entities.TimeRecord;
import com.artuur.hrms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TimeRecordRepository extends JpaRepository<TimeRecord, UUID> {
}
