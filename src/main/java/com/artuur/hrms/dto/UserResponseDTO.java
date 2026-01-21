package com.artuur.hrms.dto;

import com.artuur.hrms.entities.Role;
import com.artuur.hrms.entities.User;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record UserResponseDTO(
        UUID userId,
        String username,
        String email,
        Set<String> roles
) {
    public UserResponseDTO(User user) {
        this(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
        );
    }
}
