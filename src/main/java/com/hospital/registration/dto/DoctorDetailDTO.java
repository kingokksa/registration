package com.hospital.registration.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDetailDTO {

    private Long doctorId;
    private String specialization;
    private String level;
    private String schedule;
    private String userName;
    private String departmentName;

}