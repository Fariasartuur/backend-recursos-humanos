package com.artuur.hrms.dto;

import com.artuur.hrms.entities.WorkScale;

import java.util.List;

public record WorkScaleDTO(
        String name,
        String description,
        List<ScaleDayDTO> days
) {
    public WorkScaleDTO(WorkScale workScale) {
        this(
                workScale.getName(),
                workScale.getDescription(),
                workScale.getDays().stream().map(ScaleDayDTO::new).toList()
        );
    }
}
