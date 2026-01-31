package com.artuur.hrms.services;

import com.artuur.hrms.dto.ClockInRequest;
import com.artuur.hrms.dto.TimeRecordResponseDTO;
import com.artuur.hrms.entities.TimeRecord;
import com.artuur.hrms.enums.Type;
import com.artuur.hrms.repository.EmployeeRepository;
import com.artuur.hrms.repository.TimeRecordRepository;
import com.artuur.hrms.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TimeRecordService {

    private final TimeRecordRepository timeRecordRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public TimeRecordService(TimeRecordRepository timeRecordRepository, EmployeeRepository employeeRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.timeRecordRepository = timeRecordRepository;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public List<TimeRecord> listAllByEmployee(UUID id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));

        return timeRecordRepository.findAllByEmployee_EmployeeId(employee.getEmployeeId());
    }

    @Transactional
    public List<TimeRecordResponseDTO> listMyRecords(UUID id) {
        var employee = employeeRepository.findByUser_UserId(id)
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));

        return timeRecordRepository.findAllByEmployee_User_UserIdOrderByEntryHourDesc(id)
                .stream()
                .map(TimeRecordResponseDTO::new)
                .toList();
    }

    @Transactional
    public TimeRecord clockIn(ClockInRequest request) {
        var employee = employeeRepository.findByCpf(request.cpf())
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));

        var user = Optional.ofNullable(employee.getUser())
                .orElseThrow(() -> new BadCredentialsException("Este funcionário não possui usuário cadastrado."));

        if(!user.isLoginCorrect(request.password(), passwordEncoder)) {
            throw new BadCredentialsException("Password invalid!");
        }

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
