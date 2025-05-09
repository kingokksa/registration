package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.registration.pojo.DiagnosisRecord;
import com.hospital.registration.mapper.DiagnosisRecordMapper;
import com.hospital.registration.service.DiagnosisRecordService;
import org.springframework.stereotype.Service;

@Service
public class DiagnosisRecordServiceImpl extends ServiceImpl<DiagnosisRecordMapper, DiagnosisRecord>
                implements DiagnosisRecordService {
}
