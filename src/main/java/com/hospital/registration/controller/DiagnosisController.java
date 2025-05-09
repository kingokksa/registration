package com.hospital.registration.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diagnoses")
public class DiagnosisController {

    @PostMapping
    public Object createDiagnosis(@RequestBody Object diagnosis) {
        // 创建诊断记录
        return null;
    }

    @GetMapping("/{id}")
    public Object getDiagnosis(@PathVariable Long id) {
        // 获取诊断详情
        return null;
    }

    @PutMapping("/{id}")
    public Object updateDiagnosis(@PathVariable Long id, @RequestBody Object diagnosis) {
        // 更新诊断记录
        return null;
    }

    @GetMapping("/patient/{id}")
    public Object getPatientDiagnoses(@PathVariable Long id) {
        // 获取病人诊断记录
        return null;
    }

    @GetMapping("/doctor/{id}")
    public Object getDoctorDiagnoses(@PathVariable Long id) {
        // 获取医生诊断记录
        return null;
    }

    @PostMapping("/{id}/attachments")
    public Object uploadDiagnosisAttachment(@PathVariable Long id, @RequestBody Object attachment) {
        // 上传诊断附件
        return null;
    }

    @DeleteMapping("/{id}/attachments/{attachmentId}")
    public Object deleteDiagnosisAttachment(@PathVariable Long id, @PathVariable Long attachmentId) {
        // 删除诊断附件
        return null;
    }

    @GetMapping("/stats")
    public Object getDiagnosisStats() {
        // 获取诊断统计
        return null;
    }

    @GetMapping("/templates")
    public Object getDiagnosisTemplates() {
        // 获取诊断模板
        return null;
    }

    @PostMapping("/templates")
    public Object saveDiagnosisTemplate(@RequestBody Object template) {
        // 保存诊断模板
        return null;
    }
}
