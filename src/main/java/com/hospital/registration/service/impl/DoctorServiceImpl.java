package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.registration.pojo.Doctor;
import com.hospital.registration.pojo.User;
import com.hospital.registration.mapper.DoctorMapper;
import com.hospital.registration.service.DoctorService;
import com.hospital.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {

    @Autowired
    private UserService userService;

    @Override
    public List<Doctor> getDoctorsByDepartment(Long departmentId) {
        QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
        wrapper.eq("department_id", departmentId);
        return list(wrapper);
    }

    @Override
    public List<Doctor> getAvailableDoctors() {
        QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("schedule")
                .ne("schedule", "");
        return list(wrapper);
    }

    @Override
    public Map<String, Object> updateSchedule(Long doctorId, String schedule) {
        Doctor doctor = getById(doctorId);
        if (doctor == null) {
            return Map.of("success", false, "message", "医生不存在");
        }

        doctor.setSchedule(schedule);
        boolean success = updateById(doctor);
        return Map.of(
                "success", success,
                "message", success ? "排班信息更新成功" : "排班信息更新失败");
    }

    @Override
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
        wrapper.eq("specialization", specialization);
        return list(wrapper);
    }

    @Override
    public Map<String, Object> getDoctorDetails(Long doctorId) {
        Doctor doctor = getById(doctorId);
        if (doctor == null) {
            return Map.of("success", false, "message", "医生不存在");
        }

        User user = userService.getById(doctor.getUserId());
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("doctor", doctor);
        result.put("user", user);
        return result;
    }

    @Override
    public Doctor getByUserId(Long userId) {
        QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return getOne(wrapper);
    }
}
