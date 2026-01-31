package com.artuur.hrms.dto;

import java.util.UUID;

public record PositionDTO(
        UUID id,
        String title,
        Double salaryRangeMin,
        Double salaryRangeMax,
        Boolean active
) {
}
