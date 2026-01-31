package com.artuur.hrms.controller;
import com.artuur.hrms.dto.WorkScaleDTO;
import com.artuur.hrms.entities.Position;
import com.artuur.hrms.dto.PositionDTO;
import com.artuur.hrms.services.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_EMPLOYEE')")
    @GetMapping
    public ResponseEntity<List<Position>> listAll() {
        return ResponseEntity.ok(positionService.listAll());
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_EMPLOYEE')")
    @GetMapping("/active")
    public ResponseEntity<List<Position>> listAllActive() {
        return ResponseEntity.ok(positionService.listAllActive());
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> newPosition(@RequestBody PositionDTO dto) {
        positionService.newPosition(dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePosition(@PathVariable UUID id, @RequestBody PositionDTO dto) {
        positionService.updatePosition(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable UUID id) {
        positionService.deletePosition(id);
        return ResponseEntity.noContent().build();
    }
}
