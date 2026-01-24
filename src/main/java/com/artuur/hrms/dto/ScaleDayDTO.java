package com.artuur.hrms.dto;

import com.artuur.hrms.entities.ScaleDayConfig;

import java.time.LocalTime;

public record ScaleDayDTO(
        Integer dayOrder,
        boolean isWorkDay,
        LocalTime plannedEntry,
        LocalTime plannedExit
) {
    public ScaleDayDTO(ScaleDayConfig scaleDayConfig) {
        this(
                scaleDayConfig.getDayOrder(),
                scaleDayConfig.isWorkDay(),
                scaleDayConfig.getPlannedEntry(),
                scaleDayConfig.getPlannedExit()
        );
    }
}
