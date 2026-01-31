package com.artuur.hrms.services;

import com.artuur.hrms.dto.ScaleDayDTO;
import com.artuur.hrms.dto.WorkScaleDTO;
import com.artuur.hrms.entities.ScaleDayConfig;
import com.artuur.hrms.entities.WorkScale;
import com.artuur.hrms.repository.WorkScaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkScaleService {

    private final WorkScaleRepository workScaleRepository;

    public WorkScaleService(WorkScaleRepository workScaleRepository) {
        this.workScaleRepository = workScaleRepository;
    }

    public List<WorkScale> listAll() {
        return workScaleRepository.findAll();
    }

    public List<WorkScale> listAllActive() { return workScaleRepository.findByActiveTrue(); }

    @Transactional
    public WorkScale newWorkScale(WorkScaleDTO dto) {
        WorkScale scale = WorkScale.builder()
                .name(dto.name())
                .description(dto.description())
                .active(dto.active())
                .build();

        List<ScaleDayConfig> dayConfigs = dto.days().stream()
                .map(dayDto -> ScaleDayConfig.builder()
                        .workScale(scale)
                        .dayOrder(dayDto.dayOrder())
                        .workDay(dayDto.isWorkDay())
                        .plannedEntry(dayDto.plannedEntry())
                        .plannedExit(dayDto.plannedExit())
                        .build())
                .toList();

        scale.setDays(dayConfigs);

        return workScaleRepository.save(scale);
    }

    @Transactional
    public void deleteWorkScale(UUID id) {
        var scale = workScaleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Escala não encontrada"));

        scale.setActive(false);
        workScaleRepository.save(scale);
    }

    @Transactional
    public void updateWorkScale(UUID id, WorkScaleDTO dto) {
        var scale = workScaleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Escala não encontrada"));

        scale.setActive(dto.active());
        scale.setDescription(dto.description());
        scale.setName(dto.name());

        List<ScaleDayConfig> dayConfigs = dto.days().stream()
                .map(dayDto -> ScaleDayConfig.builder()
                        .workScale(scale)
                        .dayOrder(dayDto.dayOrder())
                        .workDay(dayDto.isWorkDay())
                        .plannedEntry(dayDto.plannedEntry())
                        .plannedExit(dayDto.plannedExit())
                        .build())
                .toList();

        scale.getDays().clear();
        scale.getDays().addAll(dayConfigs);
        workScaleRepository.save(scale);
    }
}
