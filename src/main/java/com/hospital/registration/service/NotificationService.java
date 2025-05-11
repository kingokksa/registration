package com.hospital.registration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.registration.pojo.Notification;
import java.util.List;
import java.util.Map;

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
     * 获取用户的通知列表
     */
    List<Notification> getUserNotifications(Long userId);

    /**
     * 获取通知详情（包含关联数据）
     */
    Map<String, Object> getNotificationDetails(Long notificationId);

    /**
     * 获取用户的所有通知（包含关联数据）
     */
    List<Map<String, Object>> getUserNotificationsWithDetails(Long userId);

    /**
     * 获取用户通知统计信息
     */
    Map<String, Object> getUserNotificationStats(Long userId);

    /**
     * 将通知标记为已读
     */
    boolean markAsRead(Long id);

    /**
     * 标记所有通知为已读
     */
    void markAllAsRead(Long userId);

    /**
     * 发送系统通知
     */
    boolean sendSystemNotification(String message);

    /**
     * 发送用户通知
     */
    boolean sendUserNotification(Long userId, String message, String type);

    /**
     * 发送带关联记录的用户通知
     */
    boolean sendUserNotification(Long userId, String message, String type, Long relatedId, String relatedType);
}
