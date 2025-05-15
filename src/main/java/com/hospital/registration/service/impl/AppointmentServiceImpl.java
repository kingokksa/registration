package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.registration.pojo.Appointment;
import com.hospital.registration.mapper.AppointmentMapper;
import com.hospital.registration.service.AppointmentService;
import com.hospital.registration.dto.DiagnosisAppointmentDTO;
import com.hospital.registration.mapper.DoctorMapper;
import com.hospital.registration.mapper.UserMapper;
import com.hospital.registration.pojo.Doctor;
import com.hospital.registration.pojo.User;
import java.util.ArrayList;
import com.hospital.registration.mapper.DepartmentMapper;
import com.hospital.registration.pojo.Department;
import com.hospital.registration.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> createAppointment(Appointment appointment) {
        boolean success = save(appointment);
        if (success) {
            // 发送通知给医生和患者
            notificationService.sendUserNotification(
                    appointment.getDoctorId(),
                    "您有新的预约请求",
                    "APPOINTMENT");
            notificationService.sendUserNotification(
                    appointment.getPatientId(),
                    "预约创建成功",
                    "APPOINTMENT");
        }
        return Map.of(
                "success", success,
                "message", success ? "预约创建成功" : "预约创建失败");
    }

    @Override
    public List<Appointment> getPatientAppointments(Long patientId) {
        QueryWrapper<Appointment> wrapper = new QueryWrapper<>();
        wrapper.eq("patient_id", patientId)
                .orderByDesc("appointment_date");
        return list(wrapper);
    }

    @Override
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        QueryWrapper<Appointment> wrapper = new QueryWrapper<>();
        wrapper.eq("doctor_id", doctorId)
                .orderByDesc("appointment_date");
        return list(wrapper);
    }

    @Override
    public Map<String, Object> cancelAppointment(Long appointmentId) {
        Appointment appointment = getById(appointmentId);
        if (appointment == null) {
            return Map.of("success", false, "message", "预约不存在");
        }

        appointment.setStatus("CANCELLED");
        boolean success = updateById(appointment);
        if (success) {
            // 发送通知
            notificationService.sendUserNotification(
                    appointment.getDoctorId(),
                    "预约已被取消",
                    "APPOINTMENT");
            notificationService.sendUserNotification(
                    appointment.getPatientId(),
                    "预约已取消",
                    "APPOINTMENT");
        }
        return Map.of(
                "success", success,
                "message", success ? "预约取消成功" : "预约取消失败");
    }

    @Override
    public Map<String, Object> completeAppointment(Long appointmentId) {
        Appointment appointment = getById(appointmentId);
        if (appointment == null) {
            return Map.of("success", false, "message", "预约不存在");
        }

        appointment.setStatus("COMPLETED");
        boolean success = updateById(appointment);
        if (success) {
            notificationService.sendUserNotification(
                    appointment.getPatientId(),
                    "您的预约已完成",
                    "APPOINTMENT");
        }
        return Map.of(
                "success", success,
                "message", success ? "预约完成" : "操作失败");
    }

    @Override
    public List<Appointment> getDepartmentAppointments(Long departmentId) {
        QueryWrapper<Appointment> wrapper = new QueryWrapper<>();
        wrapper.eq("department_id", departmentId)
                .orderByDesc("appointment_date");
        return list(wrapper);
    }

    @Override
    public List<DiagnosisAppointmentDTO> getAllDiagnosisAppointmentDTOs() {
        List<Appointment> appointments = list();
        List<DiagnosisAppointmentDTO> dtos = new ArrayList<>();
        for (Appointment appointment : appointments) {
            Doctor doctor = doctorMapper.selectById(appointment.getDoctorId());
            User patient = userMapper.selectById(appointment.getPatientId());
            User doctorUser = userMapper.selectById(doctor.getUserId());
            Department department = departmentMapper.selectById(doctor.getDepartmentId());
            DiagnosisAppointmentDTO dto = new DiagnosisAppointmentDTO(
                    doctorUser != null ? doctorUser.getUsername() : "未知医生",
                    department != null ? department.getDepartmentName() : "未知科室", // Get department name
                    appointment.getAppointmentDate(),
                    appointment.getAppointmentId(),
                    // appointment.getAmount(), // Appointment POJO does not have amount field
                    null, // Set amount to null or a default value
                    patient != null ? patient.getUsername() : "未知患者",
                    appointment.getStatus());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public Appointment getAppointmentById(Integer id) {
        // Note: Appointment ID is Long in POJO, but the method in interface was defined
        // as Integer.
        // Assuming the interface definition was a mistake and should be Long.
        // If the interface must remain Integer, casting might be needed, but it's
        // better to fix the interface.
        // For now, I will implement based on the POJO's Long type for consistency with
        // the database ID.
        // If the interface cannot be changed, please let me know.
        return getById(id); // This might cause issues if id is large
    }

    @Override
    public void addAppointment(Appointment appointment) {
        save(appointment);
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        updateById(appointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        removeById(id);
    }
}
