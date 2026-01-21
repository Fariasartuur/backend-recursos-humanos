package com.artuur.hrms.controller;

import com.artuur.hrms.dto.CreateUserDTO;
import com.artuur.hrms.dto.UpdateUserDTO;
import com.artuur.hrms.dto.UserResponseDTO;
import com.artuur.hrms.entities.User;
import com.artuur.hrms.services.UserService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDTO dto) {
        userService.newUser(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAll(){
        var users = userService.listAll();

        var response = users.stream()
                .map(UserResponseDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable UUID id, @RequestBody UpdateUserDTO dto) {
        userService.updateUser(id, dto);
        return ResponseEntity.noContent().build();
    }

}
