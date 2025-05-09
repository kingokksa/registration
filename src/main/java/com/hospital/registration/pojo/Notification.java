package com.hospital.registration.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Notifications")
public class Notification {
    @TableId(value = "notification_id", type = IdType.AUTO)
    private Long notificationId;
    private Long userId;
    private String message;
    private String notificationType;
    private Boolean isRead;
    private LocalDateTime createdAt;
}
