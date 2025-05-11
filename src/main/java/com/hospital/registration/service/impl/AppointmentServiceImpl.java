package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.registration.pojo.Appointment;
import com.hospital.registration.mapper.AppointmentMapper;
import com.hospital.registration.service.AppointmentService;
import com.hospital.registration.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public Map<String, Object> getAppointmentDetails(Long appointmentId) {
        return appointmentMapper.getAppointmentDetails(appointmentId);
    }

    @Override
    public List<Map<String, Object>> getPatientAppointmentsWithDetails(Long patientId) {
        return appointmentMapper.getPatientAppointments(patientId);
    }

    @Override
    public List<Map<String, Object>> getDoctorAppointmentsWithDetails(Long doctorId, Date startTime, Date endTime) {
        return appointmentMapper.getDoctorAppointments(doctorId, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getDepartmentAppointmentsWithDetails(Long departmentId) {
        return appointmentMapper.getDepartmentAppointments(departmentId);
    }

    @Override
    public Map<String, Object> getAppointmentStats(Date startDate, Date endDate) {
        return appointmentMapper.getAppointmentStats(startDate, endDate);
    }

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
                .orderByDesc("appointment_time");
        return list(wrapper);
    }

    @Override
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        QueryWrapper<Appointment> wrapper = new QueryWrapper<>();
        wrapper.eq("doctor_id", doctorId)
                .orderByDesc("appointment_time");
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
                .orderByDesc("appointment_time");
        return list(wrapper);
    }
}
