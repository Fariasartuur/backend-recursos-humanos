package com.artuur.hrms.dto;

import java.util.Set;

public record CreateUserDTO(String username, String email, String password, Set<String> roles) {
}
