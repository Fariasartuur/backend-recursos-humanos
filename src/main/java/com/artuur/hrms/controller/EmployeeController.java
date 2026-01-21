package com.artuur.hrms.controller;

import com.artuur.hrms.dto.*;
import com.artuur.hrms.services.EmployeeService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> listAll(){
        var employees = employeeService.listAll();

        var response = employees.stream()
                .map(EmployeeResponseDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> newEmployee(@RequestBody CreateEmployeeDTO dto) {
        employeeService.newEmployee(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/promote")
    public ResponseEntity<Void> promoteEmployee(@PathVariable UUID id, @RequestBody CreateUserDTO dto) {
        employeeService.promoteToUser(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable UUID id, @RequestBody UpdateEmployeeDTO dto) {
        employeeService.updateEmployee(id, dto);
        return ResponseEntity.noContent().build();
    }


}
