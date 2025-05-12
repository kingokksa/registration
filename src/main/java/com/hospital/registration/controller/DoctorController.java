package com.hospital.registration.controller;

import com.hospital.registration.pojo.Doctor;
import com.hospital.registration.pojo.Doctor;
import com.hospital.registration.pojo.User;
import com.hospital.registration.pojo.Department;
import com.hospital.registration.dto.DoctorDetailDTO;
import com.hospital.registration.service.DoctorService;
import com.hospital.registration.service.UserService;
import com.hospital.registration.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DoctorDetailDTO>> list(@RequestParam Long departmentId) {
        List<Doctor> doctors = doctorService.getDoctorsByDepartment(departmentId);
        List<DoctorDetailDTO> doctorDetailDTOs = new ArrayList<>();

        for (Doctor doctor : doctors) {
            User user = userService.getById(doctor.getUserId());
            Department department = departmentService.getById(doctor.getDepartmentId());

            DoctorDetailDTO dto = new DoctorDetailDTO();
            dto.setDoctorId(doctor.getDoctorId());
            dto.setSpecialization(doctor.getSpecialization());
            dto.setLevel(doctor.getLevel());
            dto.setSchedule(doctor.getSchedule());
            dto.setUserName(user != null ? user.getName() : "未知用户");
            dto.setDepartmentName(department != null ? department.getDepartmentName() : "未知科室");

            doctorDetailDTOs.add(dto);
        }

        return ResponseEntity.ok(doctorDetailDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> get(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> save(@RequestBody Doctor doctor) {
        return ResponseEntity.ok(Map.of(
                "success", doctorService.save(doctor),
                "message", "医生信息添加成功"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Doctor doctor) {
        doctor.setDoctorId(id);
        return ResponseEntity.ok(Map.of(
                "success", doctorService.updateById(doctor),
                "message", "医生信息更新成功"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of(
                "success", doctorService.removeById(id),
                "message", "医生信息删除成功"));
    }
}
