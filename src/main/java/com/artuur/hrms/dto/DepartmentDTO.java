package com.artuur.hrms.dto;

import java.util.UUID;

public record DepartmentDTO(UUID id, String name, Boolean active) {
}
