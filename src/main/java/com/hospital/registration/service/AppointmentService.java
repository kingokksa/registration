package com.hospital.registration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.registration.pojo.Appointment;
import java.util.List;
import com.hospital.registration.dto.DiagnosisAppointmentDTO;
import java.util.Map;

public interface AppointmentService extends IService<Appointment> {
    /**
     * 创建预约
     */
    Map<String, Object> createAppointment(Appointment appointment);

    /**
     * 获取患者的所有预约
     */
    List<Appointment> getPatientAppointments(Long patientId);

    /**
     * 获取医生的所有预约
     */
    List<Appointment> getDoctorAppointments(Long doctorId);

    /**
     * 取消预约
     */
    Map<String, Object> cancelAppointment(Long appointmentId);

    /**
     * 完成预约
     */
    Map<String, Object> completeAppointment(Long appointmentId);

    /**
     * 获取科室的预约列表
     */
    List<Appointment> getDepartmentAppointments(Long departmentId);

    // Admin CRUD methods
    List<DiagnosisAppointmentDTO> getAllDiagnosisAppointmentDTOs();

    Appointment getAppointmentById(Integer id);

    void addAppointment(Appointment appointment);

    void updateAppointment(Appointment appointment);

    void deleteAppointment(Long id);
}
