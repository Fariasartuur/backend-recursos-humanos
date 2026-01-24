package com.artuur.hrms.controller;

import com.artuur.hrms.dto.WorkScaleDTO;
import com.artuur.hrms.entities.WorkScale;
import com.artuur.hrms.services.WorkScaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scales")
public class WorkScaleController {

    private final WorkScaleService workScaleService;

    public WorkScaleController(WorkScaleService workScaleService) {
        this.workScaleService = workScaleService;
    }

    @GetMapping
    public ResponseEntity<List<WorkScaleDTO>> listAll() {
        List<WorkScaleDTO> list = workScaleService.listAll()
                .stream()
                .map(WorkScaleDTO::new)
                .toList();

        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<WorkScaleDTO> newWorkScale(@RequestBody WorkScaleDTO dto) {
        WorkScale newScale = workScaleService.newWorkScale(dto);
        WorkScaleDTO scale = new WorkScaleDTO(newScale);
        return ResponseEntity.ok(scale);
    }
}
