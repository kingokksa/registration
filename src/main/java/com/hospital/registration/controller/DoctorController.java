package com.hospital.registration.controller;

import com.hospital.registration.pojo.Doctor;
import com.hospital.registration.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

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
