package com.artuur.hrms.controller;

import com.artuur.hrms.dto.TimeRecordResponseDTO;
import com.artuur.hrms.entities.TimeRecord;
import com.artuur.hrms.services.TimeRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_MANAGER')")
    @GetMapping("/employee/{id}")
    public ResponseEntity<List<TimeRecordResponseDTO>> listAllByEmployee(@PathVariable UUID id) {
        List<TimeRecordResponseDTO> list = timeRecordService.listAllByEmployee(id)
                .stream()
                .map(TimeRecordResponseDTO::new)
                .toList();

        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_EMPLOYEE')")
    @GetMapping("/me")
    public ResponseEntity<List<TimeRecordResponseDTO>> listMyRecords(JwtAuthenticationToken token) {
        UUID id = UUID.fromString(token.getName());
        return ResponseEntity.ok(timeRecordService.listMyRecords(id));
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_MANAGER', 'SCOPE_ROLE_EMPLOYEE')")
    @PostMapping("/employee")
    public ResponseEntity<TimeRecordResponseDTO> clockIn(JwtAuthenticationToken token) {
        UUID id = UUID.fromString(token.getName());
        TimeRecord record = timeRecordService.clockIn(id);
        return ResponseEntity.ok(new TimeRecordResponseDTO(record));
    }
}
