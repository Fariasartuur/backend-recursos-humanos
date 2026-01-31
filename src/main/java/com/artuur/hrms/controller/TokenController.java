package com.artuur.hrms.controller;

import com.artuur.hrms.dto.LoginRequest;
import com.artuur.hrms.dto.LoginResponse;
import com.artuur.hrms.entities.Role;
import com.artuur.hrms.repository.UserRepository;
import com.artuur.hrms.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final TokenService service;
    private final UserRepository userRepository;

    public TokenController(TokenService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Long expiresIn = 3600L;
        var token = service.generateToken(loginRequest, expiresIn);

        var user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado"));

        var roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();


        return ResponseEntity.ok(new LoginResponse(token, expiresIn, roles));
    }
}
