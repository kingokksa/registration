package com.hospital.registration.controller;

import com.hospital.registration.pojo.Diagnosis;
import com.hospital.registration.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diagnoses")
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @PostMapping
    public Map<String, Object> createDiagnosis(@RequestBody Diagnosis diagnosis) {
        return diagnosisService.createDiagnosis(diagnosis);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getDiagnosis(@PathVariable Long id) {
        return diagnosisService.getDiagnosisDetails(id);
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateDiagnosis(@PathVariable Long id, @RequestBody Diagnosis diagnosis) {
        diagnosis.setDiagnosisId(id);
        return diagnosisService.updateDiagnosis(diagnosis);
    }

    @GetMapping("/patient/{id}")
    public List<Map<String, Object>> getPatientDiagnoses(@PathVariable Long id) {
        return diagnosisService.getPatientDiagnosesList(id);
    }

    @GetMapping("/doctor/{id}")
    public List<Map<String, Object>> getDoctorDiagnoses(@PathVariable Long id) {
        return diagnosisService.getDoctorDiagnosesList(id);
    }

    @PostMapping("/{id}/attachments")
    public Map<String, Object> uploadDiagnosisAttachment(@PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        return diagnosisService.uploadAttachment(id, file);
    }

    @DeleteMapping("/{id}/attachments/{attachmentId}")
    public boolean deleteDiagnosisAttachment(@PathVariable Long id,
            @PathVariable Long attachmentId) {
        return diagnosisService.deleteAttachment(id, attachmentId);
    }

    @GetMapping("/stats")
    public Map<String, Object> getDiagnosisStats(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return diagnosisService.getDiagnosisStats(startDate, endDate);
    }

    @GetMapping("/templates")
    public List<Map<String, Object>> getDiagnosisTemplates() {
        return diagnosisService.getDiagnosisTemplates();
    }

    @PostMapping("/templates")
    public Map<String, Object> saveDiagnosisTemplate(@RequestBody Map<String, Object> template) {
        return diagnosisService.saveDiagnosisTemplate(template);
    }
}
