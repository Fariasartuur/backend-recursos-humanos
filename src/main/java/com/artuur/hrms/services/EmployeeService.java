package com.artuur.hrms.services;

import com.artuur.hrms.dto.CreateEmployeeDTO;
import com.artuur.hrms.dto.CreateUserDTO;
import com.artuur.hrms.dto.UpdateEmployeeDTO;
import com.artuur.hrms.entities.Employee;
import com.artuur.hrms.entities.User;
import com.artuur.hrms.entities.WorkScale;
import com.artuur.hrms.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final StatusRepository statusRepository;
    private final WorkScaleRepository workScaleRepository;

    public EmployeeService(UserService userService, UserRepository userRepository, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, PositionRepository positionRepository, StatusRepository statusRepository, WorkScaleRepository workScaleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
        this.statusRepository = statusRepository;
        this.workScaleRepository = workScaleRepository;
    }

    public List<Employee> listAll() {
        return employeeRepository.findAll();
    }

    @Transactional
    public void newEmployee(CreateEmployeeDTO dto) {

        var scale = workScaleRepository.findById(dto.scaleId())
                .orElseThrow(() -> new RuntimeException("Escala não encontrada"));

        var department = departmentRepository.findById(dto.departmentId())
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));

        var position = positionRepository.findById(dto.positionId())
                .orElseThrow(() -> new RuntimeException("Posição não encontrada"));

        var status = statusRepository.findById(dto.statusId())
                .orElseThrow(() -> new RuntimeException("Status não encontrado"));

        User user = null;
        if(dto.userId() != null) {
            user = userRepository.findById(dto.userId())
                    .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        }

        var employee = Employee.builder()
                .nome(dto.nome())
                .cpf(dto.cpf())
                .admissionDate(dto.admissionDate())
                .baseSalary(dto.baseSalary())
                .status(status)
                .user(user)
                .department(department)
                .position(position)
                .healthPlan(dto.healthPlan())
                .scale(scale)
                .scaleStartDate(dto.scaleStartDate())
                .build();

        employeeRepository.save(employee);
    }

    @Transactional
    public void promoteToUser(UUID employeeId, CreateUserDTO dto) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));

        if(employee.getUser() != null) {
            throw new RuntimeException("Este funcionário já possui um usuário vinculado");
        }

        userService.newUser(dto);

        var user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        employee.setUser(user);
        employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(UUID id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));

        var dismissedStatus = statusRepository.findByName("DISMISSED")
                .orElseThrow(() -> new RuntimeException("Status não encontrado"));


        employee.setStatus(dismissedStatus);

        if(employee.getUser() != null) {
            var user = employee.getUser().getUserId();

            employee.setUser(null);
            employeeRepository.save(employee);
            userService.deleteUser(user);
        } else {
            employeeRepository.save(employee);
        }
    }

    @Transactional
    public void updateEmployee(UUID id, UpdateEmployeeDTO dto) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));

        employee.setNome(dto.nome());
        employee.setHealthPlan(dto.healthPlan());
        employee.setBaseSalary(dto.baseSalary());
        employee.setScaleStartDate(dto.scaleStartDate());

        if (dto.scaleId() != null) {
            WorkScale newScale = workScaleRepository.findById(dto.scaleId())
                    .orElseThrow(() -> new RuntimeException("Escala não encontrada"));
            employee.setScale(newScale);
        }

        if (dto.departmentId() != null) {
            var department = departmentRepository.findById(dto.departmentId())
                    .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));
            employee.setDepartment(department);
        }

        if (dto.statusId() != null) {
            var status = statusRepository.findById(dto.statusId())
                    .orElseThrow(() -> new RuntimeException("Status não encontrado"));
            employee.setStatus(status);
        }

        if (dto.positionId() != null) {
            var position = positionRepository.findById(dto.positionId())
                    .orElseThrow(() -> new RuntimeException("Posição não encontrada"));
            employee.setPosition(position);
        }

        employeeRepository.save(employee);
    }
}
