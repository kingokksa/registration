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
@RequestMapping("/diagnosis-records")
@PreAuthorize("hasRole('DOCTOR')") // 只有医生可以访问诊断记录
public class DiagnosisRecordController {

    @Autowired
    private DiagnosisRecordService diagnosisRecordService;

    @GetMapping
    public ResponseEntity<List<DiagnosisRecord>> list() {
        return ResponseEntity.ok(diagnosisRecordService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosisRecord> get(@PathVariable Long id) {
        return ResponseEntity.ok(diagnosisRecordService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody DiagnosisRecord record) {
        return ResponseEntity.ok(Map.of(
                "success", diagnosisRecordService.save(record),
                "message", "诊断记录添加成功"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody DiagnosisRecord record) {
        record.setRecordId(id);
        return ResponseEntity.ok(Map.of(
                "success", diagnosisRecordService.updateById(record),
                "message", "诊断记录更新成功"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of(
                "success", diagnosisRecordService.removeById(id),
                "message", "诊断记录删除成功"));
    }
}
