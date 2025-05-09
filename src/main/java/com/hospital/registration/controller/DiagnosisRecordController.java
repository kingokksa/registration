package com.hospital.registration.controller;

import com.hospital.registration.pojo.DiagnosisRecord;
import com.hospital.registration.service.DiagnosisRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/diagnosis-records")
public class DiagnosisRecordController {

    @Autowired
    private DiagnosisRecordService diagnosisRecordService;

    @GetMapping
    public List<DiagnosisRecord> list() {
        return diagnosisRecordService.list();
    }

    @GetMapping("/{id}")
    public DiagnosisRecord get(@PathVariable Long id) {
        return diagnosisRecordService.getById(id);
    }

    @PostMapping
    public boolean save(@RequestBody DiagnosisRecord record) {
        return diagnosisRecordService.save(record);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Long id, @RequestBody DiagnosisRecord record) {
        record.setRecordId(id);
        return diagnosisRecordService.updateById(record);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return diagnosisRecordService.removeById(id);
    }
}
