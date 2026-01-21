package com.artuur.hrms.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
