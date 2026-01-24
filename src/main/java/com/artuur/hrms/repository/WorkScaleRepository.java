package com.artuur.hrms.repository;

import com.artuur.hrms.entities.WorkScale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkScaleRepository extends JpaRepository<WorkScale, Long> {
}
