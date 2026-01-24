package com.artuur.hrms.controller;

import com.artuur.hrms.dto.*;
import com.artuur.hrms.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> listAll(){
        var employees = employeeService.listAll();

        var response = employees.stream()
                .map(EmployeeResponseDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> newEmployee(@RequestBody CreateEmployeeDTO dto) {
        employeeService.newEmployee(dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("/{id}/promote")
    public ResponseEntity<Void> promoteEmployeeToUser(@PathVariable UUID id, @RequestBody CreateUserDTO dto) {
        employeeService.promoteToUser(id, dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable UUID id, @RequestBody UpdateEmployeeDTO dto) {
        employeeService.updateEmployee(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_EMPLOYEE')")
    @GetMapping("/me")
    public ResponseEntity<EmployeeResponseDTO> getMyInfo(JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        return ResponseEntity.ok(employeeService.getMyInfo(userId));

    }


}
