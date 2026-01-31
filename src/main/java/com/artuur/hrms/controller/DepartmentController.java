package com.artuur.hrms.controller;

import com.artuur.hrms.dto.DepartmentDTO;
import com.artuur.hrms.entities.Department;
import com.artuur.hrms.entities.Position;
import com.artuur.hrms.services.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<List<Department>> listAll() {
        return ResponseEntity.ok(departmentService.listAll());
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_MANAGER')")
    @GetMapping("/active")
    public ResponseEntity<List<Department>> listAllActive() {
        return ResponseEntity.ok(departmentService.listAllActive());
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> newDepartment(@RequestBody DepartmentDTO dto) {
        departmentService.newDepartment(dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDepartment(@PathVariable UUID id, @RequestBody DepartmentDTO dto) {
        departmentService.updateDepartment(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable UUID id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

}
