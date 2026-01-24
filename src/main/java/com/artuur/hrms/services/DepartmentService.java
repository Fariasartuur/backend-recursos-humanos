package com.artuur.hrms.services;

import com.artuur.hrms.dto.DepartmentDTO;
import com.artuur.hrms.entities.Department;
import com.artuur.hrms.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> listAll(){
        return departmentRepository.findAll();
    }

    @Transactional
    public void newDepartment(DepartmentDTO dto) {
        var department = Department.builder().name(dto.name()).build();

        departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        var department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado."));

        departmentRepository.delete(department);
    }

    @Transactional
    public void updateDepartment(Long id, DepartmentDTO dto) {
        var department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado."));

        department.setName(dto.name());
        departmentRepository.save(department);
    }
}
