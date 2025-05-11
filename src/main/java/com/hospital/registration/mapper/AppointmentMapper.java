package com.hospital.registration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hospital.registration.pojo.Appointment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;
import java.util.Date;

@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {

    @Select("SELECT a.*, " +
            "p.name as patient_name, p.phone as patient_phone, " +
            "d.name as doctor_name, d.title as doctor_title, " +
            "dept.department_name, " +
            "EXISTS(SELECT 1 FROM diagnosis diag WHERE diag.appointment_id = a.appointment_id) as has_diagnosis, " +
            "EXISTS(SELECT 1 FROM payment pay WHERE pay.appointment_id = a.appointment_id AND pay.status = 'Completed') as is_paid, "
            +
            "(SELECT amount FROM payment WHERE appointment_id = a.appointment_id LIMIT 1) as amount " +
            "FROM appointment a " +
            "LEFT JOIN user p ON a.patient_id = p.user_id " +
            "LEFT JOIN doctor d ON a.doctor_id = d.doctor_id " +
            "LEFT JOIN department dept ON d.department_id = dept.department_id " +
            "WHERE a.appointment_id = #{appointmentId}")
    Map<String, Object> getAppointmentDetails(Long appointmentId);

    @Select("SELECT a.*, " +
            "d.name as doctor_name, d.title as doctor_title, " +
            "dept.department_name " +
            "FROM appointment a " +
            "LEFT JOIN doctor d ON a.doctor_id = d.doctor_id " +
            "LEFT JOIN department dept ON d.department_id = dept.department_id " +
            "WHERE a.patient_id = #{patientId} " +
            "ORDER BY a.appointment_time DESC")
    List<Map<String, Object>> getPatientAppointments(Long patientId);

    @Select("SELECT a.*, " +
            "p.name as patient_name, p.phone as patient_phone " +
            "FROM appointment a " +
            "LEFT JOIN user p ON a.patient_id = p.user_id " +
            "WHERE a.doctor_id = #{doctorId} " +
            "AND a.appointment_time >= #{startTime} " +
            "AND a.appointment_time < #{endTime} " +
            "ORDER BY a.appointment_time")
    List<Map<String, Object>> getDoctorAppointments(Long doctorId, Date startTime, Date endTime);

    @Select("SELECT COUNT(*) as total_appointments, " +
            "SUM(CASE WHEN status = 'Completed' THEN 1 ELSE 0 END) as completed_appointments, " +
            "SUM(CASE WHEN status = 'Cancelled' THEN 1 ELSE 0 END) as cancelled_appointments, " +
            "COUNT(DISTINCT patient_id) as unique_patients, " +
            "COUNT(DISTINCT doctor_id) as unique_doctors " +
            "FROM appointment " +
            "WHERE appointment_time >= #{startDate} " +
            "AND appointment_time <= #{endDate}")
    Map<String, Object> getAppointmentStats(Date startDate, Date endDate);

    @Select("SELECT a.*, " +
            "p.name as patient_name, " +
            "d.name as doctor_name, " +
            "dept.department_name " +
            "FROM appointment a " +
            "LEFT JOIN user p ON a.patient_id = p.user_id " +
            "LEFT JOIN doctor d ON a.doctor_id = d.doctor_id " +
            "LEFT JOIN department dept ON d.department_id = dept.department_id " +
            "WHERE dept.department_id = #{departmentId} " +
            "ORDER BY a.appointment_time DESC")
    List<Map<String, Object>> getDepartmentAppointments(Long departmentId);
}
