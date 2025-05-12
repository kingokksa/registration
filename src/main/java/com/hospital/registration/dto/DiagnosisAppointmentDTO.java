package com.hospital.registration.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DiagnosisAppointmentDTO {

    private String doctorName;
    private LocalDate appointmentDate;
    private Long appointmentId;
    private BigDecimal amount;
    private String patientName;

    public DiagnosisAppointmentDTO() {
    }

    public DiagnosisAppointmentDTO(String doctorName, LocalDate appointmentDate, Long appointmentId, BigDecimal amount,
            String patientName) {
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentId = appointmentId;
        this.amount = amount;
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
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
}