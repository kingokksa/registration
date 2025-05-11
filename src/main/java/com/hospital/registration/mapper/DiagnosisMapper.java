package com.hospital.registration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hospital.registration.pojo.Diagnosis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface DiagnosisMapper extends BaseMapper<Diagnosis> {

    @Select("SELECT d.*, " +
            "p.name as patient_name, p.gender as patient_gender, p.age as patient_age, " +
            "doc.name as doctor_name, doc.title as doctor_title, " +
            "dept.department_name, " +
            "(SELECT JSON_ARRAYAGG(JSON_OBJECT('attachment_id', attachment_id, 'file_name', file_name, " +
            "'file_type', file_type, 'file_url', file_url, 'upload_time', upload_time)) " +
            "FROM diagnosis_attachment WHERE diagnosis_id = d.diagnosis_id) as attachments " +
            "FROM diagnosis d " +
            "LEFT JOIN patient p ON d.patient_id = p.patient_id " +
            "LEFT JOIN doctor doc ON d.doctor_id = doc.doctor_id " +
            "LEFT JOIN department dept ON doc.department_id = dept.department_id " +
            "WHERE d.diagnosis_id = #{diagnosisId}")
    Map<String, Object> getDiagnosisDetails(Long diagnosisId);

    @Select("SELECT d.*, " +
            "doc.name as doctor_name, dept.department_name " +
            "FROM diagnosis d " +
            "LEFT JOIN doctor doc ON d.doctor_id = doc.doctor_id " +
            "LEFT JOIN department dept ON doc.department_id = dept.department_id " +
            "WHERE d.patient_id = #{patientId} " +
            "ORDER BY d.diagnosis_time DESC")
    List<Map<String, Object>> getPatientDiagnosesList(Long patientId);

    @Select("SELECT d.*, " +
            "p.name as patient_name, p.gender as patient_gender " +
            "FROM diagnosis d " +
            "LEFT JOIN patient p ON d.patient_id = p.patient_id " +
            "WHERE d.doctor_id = #{doctorId} " +
            "ORDER BY d.diagnosis_time DESC")
    List<Map<String, Object>> getDoctorDiagnosesList(Long doctorId);

    @Select("SELECT " +
            "COUNT(*) as total_diagnoses, " +
            "COUNT(DISTINCT patient_id) as total_patients, " +
            "COUNT(DISTINCT doctor_id) as total_doctors, " +
            "COUNT(CASE WHEN status = 'Completed' THEN 1 END) as completed_diagnoses " +
            "FROM diagnosis " +
            "WHERE diagnosis_time >= #{startDate} AND diagnosis_time <= #{endDate}")
    Map<String, Object> getDiagnosisStats(Date startDate, Date endDate);
}
