package com.hospital.registration.controller;

import org.springframework.web.bind.annotation.*;
import com.hospital.registration.entity.Doctor;
import com.hospital.registration.service.DoctorService;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> list() {
        return doctorService.list();
    }

    @GetMapping("/{id}")
    public Doctor get(@PathVariable Long id) {
        return doctorService.getById(id);
    }

    @PostMapping
    public boolean save(@RequestBody Doctor doctor) {
        return doctorService.save(doctor);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Long id, @RequestBody Doctor doctor) {
        doctor.setDoctorId(id);
        return doctorService.updateById(doctor);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return doctorService.removeById(id);
    }
}
