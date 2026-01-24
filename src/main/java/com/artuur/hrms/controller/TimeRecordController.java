package com.artuur.hrms.controller;

import com.artuur.hrms.dto.TimeRecordResponseDTO;
import com.artuur.hrms.entities.TimeRecord;
import com.artuur.hrms.services.TimeRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/records")
public class TimeRecordController {

    private final TimeRecordService timeRecordService;

    public TimeRecordController(TimeRecordService timeRecordService) {
        this.timeRecordService = timeRecordService;
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<List<TimeRecordResponseDTO>> listAllByEmployee(@PathVariable UUID id) {
        List<TimeRecordResponseDTO> list = timeRecordService.listAllByEmployee(id)
                .stream()
                .map(TimeRecordResponseDTO::new)
                .toList();

        return ResponseEntity.ok(list);
    }

    @PostMapping("/employee/{id}")
    public ResponseEntity<TimeRecordResponseDTO> clockIn(@PathVariable UUID id) {
        TimeRecord record = timeRecordService.clockIn(id);
        return ResponseEntity.ok(new TimeRecordResponseDTO(record));
    }
}
