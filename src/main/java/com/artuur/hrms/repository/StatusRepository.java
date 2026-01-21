package com.artuur.hrms.repository;

import com.artuur.hrms.entities.Role;
import com.artuur.hrms.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByName(String name);
}
