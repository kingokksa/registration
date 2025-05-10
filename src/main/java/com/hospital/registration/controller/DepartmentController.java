package com.hospital.registration.controller;

import com.hospital.registration.pojo.Department;
import com.hospital.registration.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

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
