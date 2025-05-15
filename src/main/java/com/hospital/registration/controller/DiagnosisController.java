package com.hospital.registration.controller;

import com.hospital.registration.pojo.DiagnosisRecord;
import com.hospital.registration.service.DiagnosisRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diagnosis")
public class DiagnosisController {

    @Autowired
    private DiagnosisRecordService diagnosisRecordService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('doctor', 'ROLE_DOCTOR')")
    public ResponseEntity<Map<String, Object>> createDiagnosis(@RequestBody DiagnosisRecord diagnosisRecord) {
        // 创建诊断记录
        return ResponseEntity.ok(diagnosisRecordService.createDiagnosisRecord(diagnosisRecord));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('doctor', 'patient', 'admin', 'ROLE_DOCTOR', 'ROLE_PATIENT', 'ROLE_ADMIN')")
    public ResponseEntity<DiagnosisRecord> getDiagnosis(@PathVariable Long id) {
        // 获取诊断详情
        DiagnosisRecord diagnosisRecord = diagnosisRecordService.getById(id);
        if (diagnosisRecord == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(diagnosisRecord);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('doctor', 'ROLE_DOCTOR')")
    public ResponseEntity<Map<String, Object>> updateDiagnosis(@PathVariable Long id,
            @RequestBody DiagnosisRecord diagnosisRecord) {
        // 更新诊断记录
        diagnosisRecord.setRecordId(id);
        return ResponseEntity.ok(diagnosisRecordService.updateDiagnosisRecord(diagnosisRecord));
    }

    @GetMapping("/patient/{id}")
    @PreAuthorize("hasAnyAuthority('doctor', 'patient', 'admin', 'ROLE_DOCTOR', 'ROLE_PATIENT', 'ROLE_ADMIN')")
    public ResponseEntity<List<DiagnosisRecord>> getPatientDiagnoses(@PathVariable Long id) {
        // 获取病人诊断记录
        return ResponseEntity.ok(diagnosisRecordService.getPatientRecords(id));
    }

    @GetMapping("/doctor/{id}")
    @PreAuthorize("hasAnyAuthority('doctor', 'admin', 'ROLE_DOCTOR', 'ROLE_ADMIN')")
    public ResponseEntity<List<DiagnosisRecord>> getDoctorDiagnoses(@PathVariable Long id) {
        // 获取医生诊断记录
        return ResponseEntity.ok(diagnosisRecordService.getDoctorRecords(id));
    }

    @GetMapping("/appointment/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<DiagnosisRecord> getAppointmentDiagnosis(@PathVariable Long id) {
        // 获取预约相关的诊断记录
        DiagnosisRecord diagnosisRecord = diagnosisRecordService.getAppointmentRecord(id);
        if (diagnosisRecord == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(diagnosisRecord);
    }

    @PostMapping("/{id}/attachments")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<String> uploadDiagnosisAttachment(@PathVariable Long id, @RequestBody Object attachment) {
        // 上传诊断附件 - 暂未实现
        return ResponseEntity.status(501).body("上传诊断附件功能暂未实现");
    }

    @DeleteMapping("/{id}/attachments/{attachmentId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<String> deleteDiagnosisAttachment(@PathVariable Long id, @PathVariable Long attachmentId) {
        // 删除诊断附件 - 暂未实现
        return ResponseEntity.status(501).body("删除诊断附件功能暂未实现");
    }

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> getDiagnosisStats() {
        // 获取诊断统计
        return ResponseEntity.ok(diagnosisRecordService.getDiagnosisStatistics());
    }

    @GetMapping("/templates")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<String> getDiagnosisTemplates() {
        // 获取诊断模板 - 暂未实现
        return ResponseEntity.status(501).body("获取诊断模板功能暂未实现");
    }

    @PostMapping("/templates")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<String> saveDiagnosisTemplate(@RequestBody Object template) {
        // 保存诊断模板 - 暂未实现
        return ResponseEntity.status(501).body("保存诊断模板功能暂未实现");
    }
}
