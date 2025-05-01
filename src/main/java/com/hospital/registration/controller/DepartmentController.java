package com.hospital.registration.controller;

import org.springframework.web.bind.annotation.*;
import com.hospital.registration.entity.Department;
import com.hospital.registration.service.DepartmentService;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> list() {
        return departmentService.list();
    }

    @GetMapping("/{id}")
    public Department get(@PathVariable Long id) {
        return departmentService.getById(id);
    }

    @PostMapping
    public boolean save(@RequestBody Department department) {
        return departmentService.save(department);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Long id, @RequestBody Department department) {
        department.setDepartmentId(id);
        return departmentService.updateById(department);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return departmentService.removeById(id);
    }
}
