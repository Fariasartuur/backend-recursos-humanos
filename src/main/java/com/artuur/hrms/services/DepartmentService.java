package com.artuur.hrms.services;

import com.artuur.hrms.dto.DepartmentDTO;
import com.artuur.hrms.entities.Department;
import com.artuur.hrms.entities.Position;
import com.artuur.hrms.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> listAll(){
        return departmentRepository.findAll();
    }

    public List<Department> listAllActive() { return departmentRepository.findByActiveTrue(); }

    @Transactional
    public void newDepartment(DepartmentDTO dto) {
        var department = Department.builder().name(dto.name()).active(dto.active()).build();

        departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartment(UUID id) {
        var department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado."));

        department.setActive(false);
        departmentRepository.save(department);
    }

    @Transactional
    public void updateDepartment(UUID id, DepartmentDTO dto) {
        var department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado."));

        department.setName(dto.name());
        department.setActive(dto.active());
        departmentRepository.save(department);
    }
}
