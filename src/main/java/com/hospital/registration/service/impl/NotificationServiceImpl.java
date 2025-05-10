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

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification>
        implements NotificationService {

    @Autowired
    private UserService userService;

    @Override
    public List<Notification> getSystemNotifications() {
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.eq("notification_type", "SYSTEM")
                .orderByDesc("created_at");
        return list(wrapper);
    }

    @Override
    public List<Notification> getCurrentUserNotifications() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return List.of();
        }

        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", currentUser.getUserId())
                .orderByDesc("created_at");
        return list(wrapper);
    }

    @Override
    public boolean markAsRead(Long id) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return false;
        }

        Notification notification = getById(id);
        if (notification == null || !notification.getUserId().equals(currentUser.getUserId())) {
            return false;
        }

        notification.setIsRead(true);
        return updateById(notification);
    }

    @Override
    public boolean sendSystemNotification(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setNotificationType("SYSTEM");
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        return save(notification);
    }

    @Override
    public boolean sendUserNotification(Long userId, String message, String type) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setNotificationType(type);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        return save(notification);
    }
}
