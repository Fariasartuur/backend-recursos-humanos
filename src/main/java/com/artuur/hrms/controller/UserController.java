package com.artuur.hrms.controller;

import com.artuur.hrms.dto.CreateUserDTO;
import com.artuur.hrms.dto.UpdateUserDTO;
import com.artuur.hrms.dto.UserResponseDTO;
import com.artuur.hrms.entities.User;
import com.artuur.hrms.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDTO dto) {
        userService.newUser(dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAll(){
        var users = userService.listAll();

        var response = users.stream()
                .map(UserResponseDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyUser(JwtAuthenticationToken token) {
        UUID id = UUID.fromString(token.getName());
        User user = userService.getMyUser(id);

        var response = new UserResponseDTO(user);
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable UUID id, @RequestBody UpdateUserDTO dto) {
        userService.updateUser(id, dto);
        return ResponseEntity.noContent().build();
    }

}
