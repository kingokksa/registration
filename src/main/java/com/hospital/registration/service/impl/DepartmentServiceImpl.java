package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.registration.mapper.DepartmentMapper;
import com.hospital.registration.pojo.Department;
import com.hospital.registration.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Map<String, Object> createDepartment(Department department) {
        this.save(department);
        return getDepartmentDetails(department.getDepartmentId());
    }

    @Override
    public Map<String, Object> updateDepartment(Department department) {
        this.updateById(department);
        return getDepartmentDetails(department.getDepartmentId());
    }

    @Override
    public Map<String, Object> getDepartmentDetails(Long departmentId) {
        return departmentMapper.getDepartmentDetails(departmentId);
    }

    @Override
    public List<Department> getAvailableDepartments() {
        // 只返回有医生的科室
        return lambdaQuery()
                .exists("SELECT 1 FROM doctor WHERE department_id = department.department_id")
                .list();
    }

    @Override
    public Map<String, Object> getDepartmentStatistics() {
        Map<String, Object> result = new HashMap<>();
        result.put("departments", departmentMapper.getDepartmentStatistics());
        result.put("totalDepartments", this.count());
        return result;
    }

    @Override
    public List<Map<String, Object>> getAvailableDepartmentsWithDoctors() {
        return departmentMapper.getAvailableDepartmentsWithDoctorCount();
    }
}
