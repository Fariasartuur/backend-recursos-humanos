package com.artuur.hrms.services;

import com.artuur.hrms.dto.LoginRequest;
import com.artuur.hrms.entities.Role;
import com.artuur.hrms.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public TokenService(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateToken(LoginRequest loginRequest, Long expiresIn) {
        var user = userRepository.findByEmail(loginRequest.email());

        if(user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("Email or Password invalid!");
        }

        var now  = Instant.now();

        var scope = user.get().getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("rh_backend")
                .subject(user.get().getUserId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .claim("scope", scope)
                .build();

        return  jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
