package com.artuur.hrms.services;

import com.artuur.hrms.entities.Position;
import com.artuur.hrms.dto.PositionDTO;
import com.artuur.hrms.repository.PositionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> listAll() {
        return positionRepository.findAll();
    }

    public List<Position> listAllActive() { return positionRepository.findByActiveTrue(); }

    @Transactional
    public void newPosition(PositionDTO dto) {
        var position = Position.builder()
                .title(dto.title())
                .salaryRangeMax(dto.salaryRangeMax())
                .salaryRangeMin(dto.salaryRangeMin())
                .active(dto.active())
                .build();

        positionRepository.save(position);
    }

    @Transactional
    public void deletePosition(UUID id) {
        var position = positionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Posição não encontrada"));

        position.setActive(false);
        positionRepository.save(position);
    }

    @Transactional
    public void updatePosition(UUID id, PositionDTO dto) {
        var position = positionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Posição não encontrada"));

        position.setActive(dto.active());
        position.setTitle(dto.title());
        position.setSalaryRangeMax(dto.salaryRangeMax());
        position.setSalaryRangeMin(dto.salaryRangeMin());

        positionRepository.save(position);
    }



}
