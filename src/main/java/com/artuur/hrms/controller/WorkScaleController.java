package com.artuur.hrms.controller;

import com.artuur.hrms.dto.WorkScaleDTO;
import com.artuur.hrms.entities.WorkScale;
import com.artuur.hrms.services.WorkScaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/scales")
public class WorkScaleController {

    private final WorkScaleService workScaleService;

    public WorkScaleController(WorkScaleService workScaleService) {
        this.workScaleService = workScaleService;
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_EMPLOYEE')")
    @GetMapping
    public ResponseEntity<List<WorkScaleDTO>> listAll() {
        List<WorkScaleDTO> list = workScaleService.listAll()
                .stream()
                .map(WorkScaleDTO::new)
                .toList();

        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_EMPLOYEE')")
    @GetMapping("/active")
    public ResponseEntity<List<WorkScaleDTO>> listAllActive() {
        List<WorkScaleDTO> list = workScaleService.listAllActive()
                .stream()
                .map(WorkScaleDTO::new)
                .toList();

        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<WorkScaleDTO> newWorkScale(@RequestBody WorkScaleDTO dto) {
        WorkScale newScale = workScaleService.newWorkScale(dto);
        WorkScaleDTO scale = new WorkScaleDTO(newScale);
        return ResponseEntity.ok(scale);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkScale(@PathVariable UUID id) {
        workScaleService.deleteWorkScale(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateWorkScale(@PathVariable UUID id, @RequestBody WorkScaleDTO dto) {
        workScaleService.updateWorkScale(id, dto);
        return ResponseEntity.noContent().build();
    }
}
