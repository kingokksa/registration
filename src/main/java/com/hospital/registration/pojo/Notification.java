package com.hospital.registration.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Notifications")
public class Notification {
    @TableId(value = "notification_id", type = IdType.AUTO)
    private Long notificationId;
    private Long userId;
    private String content;
    private String type;
    private Boolean isRead;
    private Date createTime;
    private Long relatedId;
    private String relatedType;
}
