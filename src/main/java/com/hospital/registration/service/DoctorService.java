package com.hospital.registration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.registration.pojo.Doctor;
import java.util.List;
import java.util.Map;

public interface DoctorService extends IService<Doctor> {
    /**
     * 根据科室ID获取医生列表
     */
    List<Doctor> getDoctorsByDepartment(Long departmentId);

    /**
     * 获取可预约的医生列表
     */
    List<Doctor> getAvailableDoctors();

    /**
     * 更新医生排班信息
     */
    Map<String, Object> updateSchedule(Long doctorId, String schedule);

    /**
     * 根据专业获取医生列表
     */
    List<Doctor> getDoctorsBySpecialization(String specialization);

    /**
     * 获取医生详细信息(包含用户信息)
     */
    Map<String, Object> getDoctorDetails(Long doctorId);

    /**
     * 根据用户ID获取医生信息
     */
    Doctor getByUserId(Long userId);
}
