package com.artuur.hrms.services;

import com.artuur.hrms.dto.WorkScaleDTO;
import com.artuur.hrms.entities.ScaleDayConfig;
import com.artuur.hrms.entities.WorkScale;
import com.artuur.hrms.repository.WorkScaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkScaleService {

    private final WorkScaleRepository workScaleRepository;

    public WorkScaleService(WorkScaleRepository workScaleRepository) {
        this.workScaleRepository = workScaleRepository;
    }

    public List<WorkScale> listAll() {
        return workScaleRepository.findAll();
    }

    @Transactional
    public WorkScale newWorkScale(WorkScaleDTO dto) {
        WorkScale scale = WorkScale.builder()
                .name(dto.name())
                .description(dto.description())
                .build();

        List<ScaleDayConfig> dayConfigs = dto.days().stream()
                .map(dayDto -> ScaleDayConfig.builder()
                        .workScale(scale)
                        .dayOrder(dayDto.dayOrder())
                        .isWorkDay(dayDto.isWorkDay())
                        .plannedEntry(dayDto.plannedEntry())
                        .plannedExit(dayDto.plannedExit())
                        .build())
                .toList();

        scale.setDays(dayConfigs);

        return workScaleRepository.save(scale);
    }
}
