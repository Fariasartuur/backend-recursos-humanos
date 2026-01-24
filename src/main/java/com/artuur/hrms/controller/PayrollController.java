package com.artuur.hrms.controller;

import com.artuur.hrms.dto.CreatePayrollDTO;
import com.artuur.hrms.dto.PayrollResponseDTO;
import com.artuur.hrms.services.PayrollService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payrolls")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @PostMapping
    public ResponseEntity<PayrollResponseDTO> newPayroll(@RequestBody CreatePayrollDTO dto) {
        PayrollResponseDTO response = payrollService.newPayroll(dto, dto.referenceMonth());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<List<PayrollResponseDTO>> getEmployeeHistory(@PathVariable UUID id) {
        List<PayrollResponseDTO> history = payrollService.findAllByEmployeeId(id)
                .stream()
                .map(PayrollResponseDTO::new)
                .toList();

        return ResponseEntity.ok(history);
    }

    @PostMapping("/generate")
    public ResponseEntity<PayrollResponseDTO> generateCustom(
            @RequestParam UUID employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        CreatePayrollDTO dto = new CreatePayrollDTO(employeeId, date);
        return ResponseEntity.ok(payrollService.newPayroll(dto, date));
    }




}
