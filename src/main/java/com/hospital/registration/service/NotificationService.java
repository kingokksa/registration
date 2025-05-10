package com.hospital.registration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.registration.pojo.Notification;
import java.util.List;

public interface NotificationService extends IService<Notification> {
    /**
     * 获取系统通知列表
     */
    List<Notification> getSystemNotifications();

    /**
     * 获取当前用户的通知列表
     */
    List<Notification> getCurrentUserNotifications();

    /**
     * 将通知标记为已读
     */
    boolean markAsRead(Long id);

    /**
     * 发送系统通知
     */
    boolean sendSystemNotification(String message);

    /**
     * 发送用户通知
     */
    boolean sendUserNotification(Long userId, String message, String type);
}
