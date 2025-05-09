package com.hospital.registration.controller;

import com.hospital.registration.pojo.Appointment;
import com.hospital.registration.pojo.User;
import com.hospital.registration.pojo.Doctor;
import com.hospital.registration.pojo.Department;
import com.hospital.registration.service.AppointmentService;
import com.hospital.registration.service.UserService;
import com.hospital.registration.service.DoctorService;
import com.hospital.registration.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @GetMapping
    public List<Appointment> list() {
        return appointmentService.list();
    }

    @GetMapping("/{id}")
    public AppointmentDetailDTO get(@PathVariable Long id) {
        Appointment appointment = appointmentService.getById(id);
        if (appointment == null)
            return null;
        User patient = userService.getById(appointment.getPatientId());
        Doctor doctor = doctorService.getById(appointment.getDoctorId());
        Department department = departmentService.getById(appointment.getDepartmentId());
        return new AppointmentDetailDTO(appointment, patient, doctor, department);
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

    // DTO类定义
    public static class AppointmentDetailDTO {
        private Appointment appointment;
        private User patient;
        private Doctor doctor;
        private Department department;

        public AppointmentDetailDTO(Appointment appointment, User patient, Doctor doctor, Department department) {
            this.appointment = appointment;
            this.patient = patient;
            this.doctor = doctor;
            this.department = department;
        }

        public Appointment getAppointment() {
            return appointment;
        }

        public void setAppointment(Appointment appointment) {
            this.appointment = appointment;
        }

        public User getPatient() {
            return patient;
        }

        public void setPatient(User patient) {
            this.patient = patient;
        }

        public Doctor getDoctor() {
            return doctor;
        }

        public void setDoctor(Doctor doctor) {
            this.doctor = doctor;
        }

        public Department getDepartment() {
            return department;
        }

        public void setDepartment(Department department) {
            this.department = department;
        }
    }
}
