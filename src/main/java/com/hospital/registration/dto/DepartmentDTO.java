package com.hospital.registration.dto;

import com.hospital.registration.pojo.Department;
import com.hospital.registration.pojo.Doctor;
import lombok.Data;
import java.util.List;

@Data
public class DepartmentDTO {
    private Long departmentId;
    private String departmentName;
    private String description;
    private List<Doctor> doctors;

    public static DepartmentDTO fromDepartment(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDepartmentId(department.getDepartmentId());
        dto.setDepartmentName(department.getDepartmentName());
        dto.setDescription(department.getDescription());
        return dto;
    }
}
