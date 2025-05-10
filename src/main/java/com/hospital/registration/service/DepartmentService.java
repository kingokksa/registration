package com.hospital.registration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.registration.pojo.Department;
import java.util.List;
import java.util.Map;

public interface DepartmentService extends IService<Department> {
    /**
     * 创建科室
     */
    Map<String, Object> createDepartment(Department department);

    /**
     * 更新科室信息
     */
    Map<String, Object> updateDepartment(Department department);

    /**
     * 获取科室详细信息(包含医生列表)
     */
    Map<String, Object> getDepartmentDetails(Long departmentId);

    /**
     * 获取所有可预约的科室
     */
    List<Department> getAvailableDepartments();

    /**
     * 获取科室统计信息
     */
    Map<String, Object> getDepartmentStatistics();
}
