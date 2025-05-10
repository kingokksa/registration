package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.registration.pojo.Department;
import com.hospital.registration.pojo.Doctor;
import com.hospital.registration.mapper.DepartmentMapper;
import com.hospital.registration.service.DepartmentService;
import com.hospital.registration.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Autowired
    private DoctorService doctorService;

    @Override
    public Map<String, Object> createDepartment(Department department) {
        boolean success = save(department);
        return Map.of(
                "success", success,
                "message", success ? "科室创建成功" : "科室创建失败");
    }

    @Override
    public Map<String, Object> updateDepartment(Department department) {
        boolean success = updateById(department);
        return Map.of(
                "success", success,
                "message", success ? "科室信息更新成功" : "科室信息更新失败");
    }

    @Override
    public Map<String, Object> getDepartmentDetails(Long departmentId) {
        Department department = getById(departmentId);
        if (department == null) {
            return Map.of("success", false, "message", "科室不存在");
        }

        List<Doctor> doctors = doctorService.getDoctorsByDepartment(departmentId);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("department", department);
        result.put("doctors", doctors);
        return result;
    }

    @Override
    public List<Department> getAvailableDepartments() {
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        // 可以添加一些过滤条件，比如状态为可用的科室
        return list(wrapper);
    }

    @Override
    public Map<String, Object> getDepartmentStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // 科室总数
        stats.put("totalDepartments", count());

        // 各科室医生数量统计
        List<Department> departments = list();
        Map<String, Integer> doctorCounts = new HashMap<>();

        for (Department dept : departments) {
            List<Doctor> doctors = doctorService.getDoctorsByDepartment(dept.getDepartmentId());
            doctorCounts.put(dept.getDepartmentName(), doctors.size());
        }

        stats.put("doctorCounts", doctorCounts);

        return stats;
    }
}
