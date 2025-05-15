package com.hospital.registration.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.hospital.registration.pojo.Appointment;
import com.hospital.registration.dto.DiagnosisAppointmentDTO;
import com.hospital.registration.pojo.Doctor;
import com.hospital.registration.pojo.User;
import com.hospital.registration.service.AppointmentService;
import com.hospital.registration.service.DoctorService;
import com.hospital.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") // 只有管理员可以访问这些接口
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    // 用户管理接口

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/users")
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setUserId(id);
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(Map.of("success", true));
    }

    // 医生管理接口

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Integer id) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/doctors")
    public ResponseEntity<Void> addDoctor(@RequestBody Doctor doctor) {
        doctorService.addDoctor(doctor);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<Void> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        doctor.setDoctorId(id);
        doctorService.updateDoctor(doctor);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok(Map.of("success", true));
    }

    // 预约管理接口

    @GetMapping("/appointments")
    public ResponseEntity<List<DiagnosisAppointmentDTO>> getAllAppointments() {
        List<DiagnosisAppointmentDTO> appointments = appointmentService.getAllDiagnosisAppointmentDTOs();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Integer id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/appointments")
    public ResponseEntity<Void> addAppointment(@RequestBody Appointment appointment) {
        appointmentService.addAppointment(appointment);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/appointments/{id}")
    public ResponseEntity<Void> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        appointment.setAppointmentId(id);
        appointmentService.updateAppointment(appointment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok(Map.of("success", true));
    }

    // 保留原有的接口，如果需要的话
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getSystemStats() {
        // TODO: 实现系统统计数据的获取
        return ResponseEntity.ok(Map.of(
                "totalUsers", 100,
                "totalDoctors", 20,
                "totalAppointments", 150));
    }

    @GetMapping("/logs")
    public ResponseEntity<Map<String, Object>> getSystemLogs() {
        // TODO: 实现系统日志的获取
        return ResponseEntity.ok(Map.of(
                "logs", new String[] { "log1", "log2", "log3" },
                "total", 3));
    }

    @GetMapping("/{type}")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
}
