package com.artuur.hrms.controller;

import com.artuur.hrms.dto.DepartmentDTO;
import com.artuur.hrms.entities.Department;
import com.artuur.hrms.services.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<Department>> listAll() {
        return ResponseEntity.ok(departmentService.listAll());
    }

    @PostMapping
    public ResponseEntity<Void> newDepartment(@RequestBody DepartmentDTO dto) {
        departmentService.newDepartment(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO dto) {
        departmentService.updateDepartment(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

}
