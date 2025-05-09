package com.hospital.registration.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("DiagnosisRecords")
public class DiagnosisRecord {
    @TableId(value = "record_id", type = IdType.AUTO)
    private Long recordId;
    private Long appointmentId;
    private Long doctorId;
    private Long patientId;
    private String description;
    private LocalDateTime createdAt;
}
