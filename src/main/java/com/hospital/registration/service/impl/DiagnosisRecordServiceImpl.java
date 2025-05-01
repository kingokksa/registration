package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.hospital.registration.entity.DiagnosisRecord;
import com.hospital.registration.mapper.DiagnosisRecordMapper;
import com.hospital.registration.service.DiagnosisRecordService;

@Service
public class DiagnosisRecordServiceImpl extends ServiceImpl<DiagnosisRecordMapper, DiagnosisRecord>
                implements DiagnosisRecordService {
}
