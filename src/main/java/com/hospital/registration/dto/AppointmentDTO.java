package com.hospital.registration.dto;

import lombok.Data;
import java.util.Date;

@Data
public class AppointmentDTO {
    private Long appointmentId;
    private Long patientId;
    private String patientName;
    private String patientPhone;
    private Long doctorId;
    private String doctorName;
    private String doctorTitle;
    private Long departmentId;
    private String departmentName;
    private Date appointmentTime;
    private String status;
    private String symptoms;
    private Date createTime;
    private Boolean hasDiagnosis;
    private Boolean isPaid;
    private Double amount;
}
