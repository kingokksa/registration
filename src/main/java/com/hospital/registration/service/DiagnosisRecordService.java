package com.hospital.registration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.registration.pojo.DiagnosisRecord;
import java.util.List;
import java.util.Map;

public interface DiagnosisRecordService extends IService<DiagnosisRecord> {
    /**
     * 创建诊断记录
     */
    Map<String, Object> createDiagnosisRecord(DiagnosisRecord record);

    /**
     * 获取患者的诊断记录
     */
    List<DiagnosisRecord> getPatientRecords(Long patientId);

    /**
     * 获取医生的诊断记录
     */
    List<DiagnosisRecord> getDoctorRecords(Long doctorId);

    /**
     * 更新诊断记录
     */
    Map<String, Object> updateDiagnosisRecord(DiagnosisRecord record);

    /**
     * 获取预约相关的诊断记录
     */
    DiagnosisRecord getAppointmentRecord(Long appointmentId);

    /**
     * 获取诊断记录统计信息
     */
    Map<String, Object> getDiagnosisStatistics();
}
