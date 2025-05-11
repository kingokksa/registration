package com.hospital.registration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hospital.registration.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {

    @Select("SELECT d.*, " +
            "(SELECT COUNT(*) FROM doctor WHERE department_id = d.department_id) as doctor_count, " +
            "(SELECT COUNT(*) FROM appointment a " +
            "JOIN doctor doc ON a.doctor_id = doc.doctor_id " +
            "WHERE doc.department_id = d.department_id) as appointment_count, " +
            "(SELECT JSON_ARRAYAGG(JSON_OBJECT('doctor_id', doctor_id, 'name', name, 'title', title)) " +
            "FROM doctor WHERE department_id = d.department_id) as doctors " +
            "FROM department d " +
            "WHERE d.department_id = #{departmentId}")
    Map<String, Object> getDepartmentDetails(Long departmentId);

    @Select("SELECT d.*, " +
            "(SELECT COUNT(*) FROM doctor WHERE department_id = d.department_id) as doctor_count " +
            "FROM department d " +
            "WHERE EXISTS (SELECT 1 FROM doctor WHERE department_id = d.department_id)")
    List<Map<String, Object>> getAvailableDepartmentsWithDoctorCount();

    @Select("SELECT d.department_id, d.department_name, " +
            "COUNT(DISTINCT doc.doctor_id) as total_doctors, " +
            "COUNT(DISTINCT a.appointment_id) as total_appointments " +
            "FROM department d " +
            "LEFT JOIN doctor doc ON d.department_id = doc.department_id " +
            "LEFT JOIN appointment a ON doc.doctor_id = a.doctor_id " +
            "GROUP BY d.department_id, d.department_name")
    List<Map<String, Object>> getDepartmentStatistics();
}
