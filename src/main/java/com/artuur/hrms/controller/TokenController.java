package com.artuur.hrms.controller;

import com.artuur.hrms.dto.LoginRequest;
import com.artuur.hrms.dto.LoginResponse;
import com.artuur.hrms.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final TokenService service;

    public TokenController(TokenService service) {
        this.service = service;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Long expiresIn = 3600L;
        var token = service.generateToken(loginRequest, expiresIn);
        return ResponseEntity.ok(new LoginResponse(token, expiresIn));
    }
}
