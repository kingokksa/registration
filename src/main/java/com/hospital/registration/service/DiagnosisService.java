package com.hospital.registration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.registration.pojo.Diagnosis;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DiagnosisService extends IService<Diagnosis> {
    Map<String, Object> createDiagnosis(Diagnosis diagnosis);

    Map<String, Object> getDiagnosisDetails(Long diagnosisId);

    Map<String, Object> updateDiagnosis(Diagnosis diagnosis);

    List<Map<String, Object>> getPatientDiagnosesList(Long patientId);

    List<Map<String, Object>> getDoctorDiagnosesList(Long doctorId);

    Map<String, Object> uploadAttachment(Long diagnosisId, MultipartFile file);

    boolean deleteAttachment(Long diagnosisId, Long attachmentId);

    Map<String, Object> getDiagnosisStats(Date startDate, Date endDate);

    List<Map<String, Object>> getDiagnosisTemplates();

    Map<String, Object> saveDiagnosisTemplate(Map<String, Object> template);
}
