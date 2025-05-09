package com.hospital.registration.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Appointments")
public class Appointment {
    @TableId(value = "appointment_id", type = IdType.AUTO)
    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private Long departmentId;
    private LocalDate appointmentDate;
    private String status;
}
