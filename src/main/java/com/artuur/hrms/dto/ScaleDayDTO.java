package com.artuur.hrms.dto;

import com.artuur.hrms.entities.ScaleDayConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public record ScaleDayDTO(
        Integer dayOrder,
        @JsonProperty("isWorkDay")
        Boolean isWorkDay,
        LocalTime plannedEntry,
        LocalTime plannedExit
) {
    public ScaleDayDTO(ScaleDayConfig scaleDayConfig) {
        this(
                scaleDayConfig.getDayOrder(),
                scaleDayConfig.getWorkDay(),
                scaleDayConfig.getPlannedEntry(),
                scaleDayConfig.getPlannedExit()
        );
    }
}
