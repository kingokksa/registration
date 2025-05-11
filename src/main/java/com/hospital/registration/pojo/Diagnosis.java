package com.hospital.registration.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@Data
@TableName("diagnosis")
public class Diagnosis {
    @TableId(type = IdType.AUTO)
    private Long diagnosisId;
    private Long patientId;
    private Long doctorId;
    private Long appointmentId;
    private String diagnosis;
    private String prescription;
    private String notes;
    private Date diagnosisTime;
    private String status;
    private Date createTime;
    private Date updateTime;
}
