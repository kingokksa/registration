package com.hospital.registration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hospital.registration.pojo.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface NotificationMapper extends BaseMapper<Notification> {

    @Select("SELECT n.*, u.name as user_name, " +
            "CASE n.related_type " +
            "  WHEN 'APPOINTMENT' THEN (" +
            "    SELECT JSON_OBJECT('appointment_id', a.appointment_id, " +
            "                      'doctor_name', d.name, " +
            "                      'patient_name', p.name, " +
            "                      'appointment_time', a.appointment_time, " +
            "                      'status', a.status) " +
            "    FROM appointment a " +
            "    LEFT JOIN doctor d ON a.doctor_id = d.doctor_id " +
            "    LEFT JOIN user p ON a.patient_id = p.user_id " +
            "    WHERE a.appointment_id = n.related_id" +
            "  ) " +
            "  WHEN 'DIAGNOSIS' THEN (" +
            "    SELECT JSON_OBJECT('diagnosis_id', d.diagnosis_id, " +
            "                      'doctor_name', doc.name, " +
            "                      'diagnosis_time', d.create_time, " +
            "                      'status', d.status) " +
            "    FROM diagnosis d " +
            "    LEFT JOIN doctor doc ON d.doctor_id = doc.doctor_id " +
            "    WHERE d.diagnosis_id = n.related_id" +
            "  ) " +
            "  ELSE NULL " +
            "END as related_data " +
            "FROM notification n " +
            "LEFT JOIN user u ON n.user_id = u.user_id " +
            "WHERE n.notification_id = #{notificationId}")
    Map<String, Object> getNotificationDetails(Long notificationId);

    @Select("SELECT n.*, u.name as user_name, " +
            "CASE n.related_type " +
            "  WHEN 'APPOINTMENT' THEN (" +
            "    SELECT JSON_OBJECT('appointment_id', a.appointment_id, " +
            "                      'appointment_time', a.appointment_time, " +
            "                      'status', a.status) " +
            "    FROM appointment a " +
            "    WHERE a.appointment_id = n.related_id" +
            "  ) " +
            "  WHEN 'DIAGNOSIS' THEN (" +
            "    SELECT JSON_OBJECT('diagnosis_id', d.diagnosis_id, " +
            "                      'diagnosis_time', d.create_time) " +
            "    FROM diagnosis d " +
            "    WHERE d.diagnosis_id = n.related_id" +
            "  ) " +
            "  ELSE NULL " +
            "END as related_data " +
            "FROM notification n " +
            "LEFT JOIN user u ON n.user_id = u.user_id " +
            "WHERE n.user_id = #{userId} " +
            "ORDER BY n.create_time DESC")
    List<Map<String, Object>> getUserNotifications(Long userId);

    @Select("SELECT COUNT(*) as total_count, " +
            "SUM(CASE WHEN is_read = 0 THEN 1 ELSE 0 END) as unread_count " +
            "FROM notification " +
            "WHERE user_id = #{userId}")
    Map<String, Object> getUserNotificationStats(Long userId);
}
