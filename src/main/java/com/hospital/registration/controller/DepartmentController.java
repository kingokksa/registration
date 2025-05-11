package com.hospital.registration.controller;

import com.hospital.registration.pojo.Department;
import com.hospital.registration.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<Map<String, Object>> list() {
        List<Department> departments = departmentService.getAvailableDepartments();
        return departments.stream()
                .map(dept -> departmentService.getDepartmentDetails(dept.getDepartmentId()))
                .toList();
    }

    @GetMapping("/{id}")
    public Map<String, Object> get(@PathVariable Long id) {
        return departmentService.getDepartmentDetails(id);
    }

    @PostMapping
    public Map<String, Object> save(@RequestBody Department department) {
        return departmentService.createDepartment(department);
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody Department department) {
        department.setDepartmentId(id);
        return departmentService.updateDepartment(department);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return departmentService.removeById(id);
    }
}
