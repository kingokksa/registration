package com.hospital.registration.dto;

import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DiagnosisAppointmentDTO {

    private String doctorName;
    private String departmentName; // Added department name
    private LocalDateTime appointmentDate;
    private Long appointmentId;
    private BigDecimal amount;
    private String patientName;
    private String status;

    public DiagnosisAppointmentDTO() {
    }

    public DiagnosisAppointmentDTO(String doctorName, String departmentName, LocalDateTime appointmentDate,
            Long appointmentId,
            BigDecimal amount,
            String patientName, String status) {
        this.doctorName = doctorName;
        this.departmentName = departmentName;
        this.appointmentDate = appointmentDate;
        this.appointmentId = appointmentId;
        this.amount = amount;
        this.patientName = patientName;
        this.status = status;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}