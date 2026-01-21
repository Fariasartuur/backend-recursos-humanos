package com.artuur.hrms.repository;

import com.artuur.hrms.entities.Position;
import com.artuur.hrms.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
}
