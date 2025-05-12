package com.hospital.registration.controller;

import com.hospital.registration.pojo.Appointment;
import com.hospital.registration.pojo.User;
import com.hospital.registration.pojo.Doctor;
import com.hospital.registration.pojo.Payment;
import com.hospital.registration.dto.DiagnosisAppointmentDTO;
import com.hospital.registration.service.AppointmentService;
import com.hospital.registration.service.UserService;
import com.hospital.registration.service.DoctorService;
import com.hospital.registration.service.DepartmentService;
import com.hospital.registration.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public List<DiagnosisAppointmentDTO> list() {
        List<Appointment> appointments = appointmentService.list();
        List<DiagnosisAppointmentDTO> diagnosisAppointmentDTOs = new ArrayList<>();

        for (Appointment appointment : appointments) {
            User patient = userService.getById(appointment.getPatientId());
            Doctor doctor = doctorService.getById(appointment.getDoctorId());
            User doctorUser = null;
            if (doctor != null) {
                doctorUser = userService.getById(doctor.getUserId());
            }
            Payment payment = paymentService.getAppointmentPayment(appointment.getAppointmentId());

            DiagnosisAppointmentDTO dto = new DiagnosisAppointmentDTO();
            dto.setAppointmentId(appointment.getAppointmentId());
            dto.setAppointmentDate(appointment.getAppointmentDate());
            dto.setPatientName(patient != null ? patient.getName() : "未知患者");
            dto.setDoctorName(doctorUser != null ? doctorUser.getName() : "未知医生");
            dto.setAmount(payment != null ? payment.getAmount() : BigDecimal.ZERO);
            dto.setStatus(appointment.getStatus());

            diagnosisAppointmentDTOs.add(dto);
        }

        return diagnosisAppointmentDTOs;
    }

    @GetMapping("/user")
    public List<DiagnosisAppointmentDTO> getUserAppointments() {
        // 获取当前认证用户
        User currentUser = userService.getCurrentUser();
        List<Appointment> appointments = new ArrayList<>();

        if (currentUser != null) {
            if ("patient".equals(currentUser.getRole())) { // 患者角色
                appointments = appointmentService.getPatientAppointments(currentUser.getUserId());
            } else if ("doctor".equals(currentUser.getRole())) { // 医生角色
                // 根据 UserID 找到对应的 DoctorID
                Doctor doctor = doctorService.getByUserId(currentUser.getUserId());
                if (doctor != null) {
                    appointments = appointmentService.getDoctorAppointments(doctor.getDoctorId());
                }
            }
        }

        List<DiagnosisAppointmentDTO> diagnosisAppointmentDTOs = new ArrayList<>();

        for (Appointment appointment : appointments) {
            User patient = userService.getById(appointment.getPatientId());
            Doctor doctor = doctorService.getById(appointment.getDoctorId());
            User doctorUser = null;
            if (doctor != null) {
                doctorUser = userService.getById(doctor.getUserId());
            }
            Payment payment = paymentService.getAppointmentPayment(appointment.getAppointmentId());

            DiagnosisAppointmentDTO dto = new DiagnosisAppointmentDTO();
            dto.setAppointmentId(appointment.getAppointmentId());
            dto.setAppointmentDate(appointment.getAppointmentDate());
            dto.setPatientName(patient != null ? patient.getName() : "未知患者");
            dto.setDoctorName(doctorUser != null ? doctorUser.getName() : "未知医生");
            dto.setAmount(payment != null ? payment.getAmount() : BigDecimal.ZERO);
            dto.setStatus(appointment.getStatus());

            diagnosisAppointmentDTOs.add(dto);
        }

        return diagnosisAppointmentDTOs;
    }

    @PostMapping
    public boolean save(@RequestBody Appointment appointment) {
        return appointmentService.save(appointment);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Long id, @RequestBody Appointment appointment) {
        appointment.setAppointmentId(id);
        return appointmentService.updateById(appointment);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return appointmentService.removeById(id);
    }
}
