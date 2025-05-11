package com.hospital.registration.dto;

import lombok.Data;
import java.util.Date;

@Data
public class NotificationDTO {
    private Long notificationId;
    private Long userId;
    private String userName;
    private String content;
    private String type;
    private Boolean isRead;
    private Date createTime;
    private Long relatedId; // 相关联的记录ID（如预约ID、诊断ID等）
    private String relatedType; // 关联记录的类型（如APPOINTMENT、DIAGNOSIS等）
    private Object relatedData; // 关联记录的详细信息
}
