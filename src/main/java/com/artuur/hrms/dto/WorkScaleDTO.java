package com.artuur.hrms.dto;

import com.artuur.hrms.entities.WorkScale;

import java.util.List;
import java.util.UUID;

public record WorkScaleDTO(
        UUID scaleId,
        String name,
        String description,
        List<ScaleDayDTO> days,
        Boolean active
) {
    public WorkScaleDTO(WorkScale workScale) {
        this(
                workScale.getScaleId(),
                workScale.getName(),
                workScale.getDescription(),
                workScale.getDays().stream().map(ScaleDayDTO::new).toList(),
                workScale.getActive()
        );
    }
}
