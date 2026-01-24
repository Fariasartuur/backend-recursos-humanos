package com.artuur.hrms.services;

import com.artuur.hrms.entities.TimeRecord;
import com.artuur.hrms.enums.Type;
import com.artuur.hrms.repository.EmployeeRepository;
import com.artuur.hrms.repository.TimeRecordRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class TimeRecordService {

    private final TimeRecordRepository timeRecordRepository;
    private final EmployeeRepository employeeRepository;

    public TimeRecordService(TimeRecordRepository timeRecordRepository, EmployeeRepository employeeRepository) {
        this.timeRecordRepository = timeRecordRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public List<TimeRecord> listAllByEmployee(UUID id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));

        return timeRecordRepository.findAllByEmployee_EmployeeId(employee.getEmployeeId());
    }

    @Transactional
    public TimeRecord clockIn(UUID id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));

        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();

        TimeRecord record = timeRecordRepository.findByEmployee_EmployeeIdAndDate(employee.getEmployeeId(), today)
                .orElseGet(() -> TimeRecord.builder()
                        .employee(employee)
                        .date(today)
                        .type(Type.ON_SITE)
                        .validated(false)
                        .build());

        if(record.getEntryHour() == null) {
            record.setEntryHour(time);
        } else if(record.getBreakStart() == null) {
            record.setBreakStart(time);
        } else if (record.getBreakEnd() == null) {
            record.setBreakEnd(time);
        } else if (record.getExitHour() == null) {
            record.setExitHour(time);
        } else {
            throw new RuntimeException("Todos os pontos de hoje já foram registados.");
        }
        return timeRecordRepository.save(record);
    }
}
