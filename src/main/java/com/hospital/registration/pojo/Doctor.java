package com.hospital.registration.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Doctors")
public class Doctor {
    @TableId(value = "doctor_id", type = IdType.AUTO)
    private Long doctorId;
    private Long userId;
    private Long departmentId;
    private String specialization;
    private String level;
    private String schedule;
}
