package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.registration.pojo.Notification;
import com.hospital.registration.pojo.User;
import com.hospital.registration.mapper.NotificationMapper;
import com.hospital.registration.service.NotificationService;
import com.hospital.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Date;

@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification>
        implements NotificationService {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public List<Notification> getSystemNotifications() {
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.eq("type", "SYSTEM")
                .orderByDesc("create_time");
        return list(wrapper);
    }

    @Override
    public List<Notification> getCurrentUserNotifications() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return List.of();
        }
        return getUserNotifications(currentUser.getUserId());
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .orderByDesc("create_time");
        return list(wrapper);
    }

    @Override
    public Map<String, Object> getNotificationDetails(Long notificationId) {
        return notificationMapper.getNotificationDetails(notificationId);
    }

    @Override
    public List<Map<String, Object>> getUserNotificationsWithDetails(Long userId) {
        return notificationMapper.getUserNotifications(userId);
    }

    @Override
    public Map<String, Object> getUserNotificationStats(Long userId) {
        return notificationMapper.getUserNotificationStats(userId);
    }

    @Override
    public boolean markAsRead(Long id) {
        Notification notification = getById(id);
        if (notification == null) {
            return false;
        }

        notification.setIsRead(true);
        return updateById(notification);
    }

    @Override
    public void markAllAsRead(Long userId) {
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .eq("is_read", false);

        Notification update = new Notification();
        update.setIsRead(true);
        update(update, wrapper);
    }

    @Override
    public boolean sendSystemNotification(String message) {
        Notification notification = new Notification();
        notification.setContent(message);
        notification.setType("SYSTEM");
        notification.setIsRead(false);
        notification.setCreateTime(new Date());
        return save(notification);
    }

    @Override
    public boolean sendUserNotification(Long userId, String message, String type) {
        return sendUserNotification(userId, message, type, null, null);
    }

    @Override
    public boolean sendUserNotification(Long userId, String message, String type, Long relatedId, String relatedType) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setContent(message);
        notification.setType(type);
        notification.setRelatedId(relatedId);
        notification.setRelatedType(relatedType);
        notification.setIsRead(false);
        notification.setCreateTime(new Date());
        return save(notification);
    }
}
