package com.hospital.registration.controller;

import com.hospital.registration.dto.NotificationDTO;
import com.hospital.registration.pojo.Notification;
import com.hospital.registration.pojo.User;
import com.hospital.registration.service.NotificationService;
import com.hospital.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @GetMapping("/system")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Notification>> getSystemNotifications() {
        return ResponseEntity.ok(notificationService.getSystemNotifications());
    }

    @PostMapping("/system")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> sendSystemNotification(@RequestBody Notification notification) {
        return ResponseEntity.ok(Map.of(
                "success", notificationService.sendSystemNotification(notification.getContent()),
                "message", "系统通知发送成功"));
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getUserNotifications() {
        return ResponseEntity.ok(notificationService.getCurrentUserNotifications());
    }

    @GetMapping("/details")
    public ResponseEntity<List<Map<String, Object>>> getUserNotificationsWithDetails() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(notificationService.getUserNotificationsWithDetails(currentUser.getUserId()));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getUserNotificationStats() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(notificationService.getUserNotificationStats(currentUser.getUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getNotificationDetails(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Notification notification) {
        notification.setNotificationId(id);
        return ResponseEntity.ok(Map.of(
                "success", notificationService.updateById(notification),
                "message", "通知更新成功"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of(
                "success", notificationService.removeById(id),
                "message", "通知删除成功"));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Map<String, Object>> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of(
                "success", notificationService.markAsRead(id),
                "message", "通知已标记为已读"));
    }

    @PutMapping("/read-all")
    public ResponseEntity<Map<String, Object>> markAllAsRead() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.badRequest().build();
        }
        notificationService.markAllAsRead(currentUser.getUserId());
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "所有通知已标记为已读"));
    }
}
