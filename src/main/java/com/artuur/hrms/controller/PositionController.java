package com.artuur.hrms.controller;
import com.artuur.hrms.entities.Position;
import com.artuur.hrms.dto.PositionDTO;
import com.artuur.hrms.services.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public ResponseEntity<List<Position>> listAll() {
        return ResponseEntity.ok(positionService.listAll());
    }

    @PostMapping
    public ResponseEntity<Void> newPosition(@RequestBody PositionDTO dto) {
        positionService.newPosition(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePosition(@PathVariable Long id, @RequestBody PositionDTO dto) {
        positionService.updatePosition(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return ResponseEntity.noContent().build();
    }
}
