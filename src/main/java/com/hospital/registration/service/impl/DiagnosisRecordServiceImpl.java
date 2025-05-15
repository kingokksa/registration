package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.registration.pojo.DiagnosisRecord;
import com.hospital.registration.pojo.Appointment;
import com.hospital.registration.mapper.DiagnosisRecordMapper;
import com.hospital.registration.service.DiagnosisRecordService;
import com.hospital.registration.service.AppointmentService;
import com.hospital.registration.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiagnosisRecordServiceImpl extends ServiceImpl<DiagnosisRecordMapper, DiagnosisRecord>
        implements DiagnosisRecordService {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private NotificationService notificationService;

    @Override
    @Transactional
    public Map<String, Object> createDiagnosisRecord(DiagnosisRecord record) {
        record.setCreatedAt(LocalDateTime.now());
        boolean success = save(record);

        if (success) {
            // 更新预约状态
            Appointment appointment = appointmentService.getById(record.getAppointmentId());
            if (appointment != null) {
                appointment.setStatus("confirmed");
                appointmentService.updateById(appointment);

                // // 发送通知
                // notificationService.sendUserNotification(
                // record.getPatientId(),
                // "您的诊断记录已创建",
                // "DIAGNOSIS");
            }
        }

        return Map.of(
                "success", success,
                "message", success ? "诊断记录创建成功" : "诊断记录创建失败");
    }

    @Override
    public List<DiagnosisRecord> getPatientRecords(Long patientId) {
        QueryWrapper<DiagnosisRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("patient_id", patientId)
                .orderByDesc("created_at");
        return list(wrapper);
    }

    @Override
    public List<DiagnosisRecord> getDoctorRecords(Long doctorId) {
        QueryWrapper<DiagnosisRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("doctor_id", doctorId)
                .orderByDesc("created_at");
        return list(wrapper);
    }

    @Override
    public Map<String, Object> updateDiagnosisRecord(DiagnosisRecord record) {
        boolean success = updateById(record);
        if (success) {
            notificationService.sendUserNotification(
                    record.getPatientId(),
                    "您的诊断记录已更新",
                    "DIAGNOSIS");
        }
        return Map.of(
                "success", success,
                "message", success ? "诊断记录更新成功" : "诊断记录更新失败");
    }

    @Override
    public DiagnosisRecord getAppointmentRecord(Long appointmentId) {
        QueryWrapper<DiagnosisRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("appointment_id", appointmentId);
        return getOne(wrapper);
    }

    @Override
    public Map<String, Object> getDiagnosisStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // 总诊断记录数
        stats.put("totalRecords", count());

        // 今日诊断记录数
        QueryWrapper<DiagnosisRecord> todayWrapper = new QueryWrapper<>();
        todayWrapper.apply("DATE(created_at) = CURDATE()");
        stats.put("todayRecords", count(todayWrapper));

        // 各医生诊断记录数统计
        // TODO: 实现医生诊断记录数统计

        return stats;
    }
}
