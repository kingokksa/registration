package com.hospital.registration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.registration.pojo.Appointment;
import java.util.List;
import java.util.Map;
import java.util.Date;

public interface AppointmentService extends IService<Appointment> {
    /**
     * 创建预约
     */
    Map<String, Object> createAppointment(Appointment appointment);

    /**
     * 获取预约详情，包含患者和医生信息
     */
    Map<String, Object> getAppointmentDetails(Long appointmentId);

    /**
     * 获取患者的所有预约及相关信息
     */
    List<Map<String, Object>> getPatientAppointmentsWithDetails(Long patientId);

    /**
     * 获取医生在指定时间段的预约及相关信息
     */
    List<Map<String, Object>> getDoctorAppointmentsWithDetails(Long doctorId, Date startTime, Date endTime);

    /**
     * 获取科室的预约列表及相关信息
     */
    List<Map<String, Object>> getDepartmentAppointmentsWithDetails(Long departmentId);

    /**
     * 获取指定时间段内的预约统计信息
     */
    Map<String, Object> getAppointmentStats(Date startDate, Date endDate);

    /**
     * 取消预约
     */
    Map<String, Object> cancelAppointment(Long appointmentId);

    /**
     * 完成预约
     */
    Map<String, Object> completeAppointment(Long appointmentId);

    /**
     * 获取患者的所有预约（基础信息）
     */
    List<Appointment> getPatientAppointments(Long patientId);

    /**
     * 获取医生的所有预约（基础信息）
     */
    List<Appointment> getDoctorAppointments(Long doctorId);

    /**
     * 获取科室的预约列表（基础信息）
     */
    List<Appointment> getDepartmentAppointments(Long departmentId);
}
