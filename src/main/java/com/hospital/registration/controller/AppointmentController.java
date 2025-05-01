package com.hospital.registration.controller;

import org.springframework.web.bind.annotation.*;
import com.hospital.registration.entity.Appointment;
import com.hospital.registration.service.AppointmentService;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> list() {
        return appointmentService.list();
    }

    @GetMapping("/{id}")
    public Appointment get(@PathVariable Long id) {
        return appointmentService.getById(id);
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
