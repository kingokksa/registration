package com.hospital.registration.controller;

import com.hospital.registration.pojo.Notification;
import com.hospital.registration.service.NotificationService;
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

    @GetMapping("/system")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Notification>> getSystemNotifications() {
        return ResponseEntity.ok(notificationService.getSystemNotifications());
    }

    @PostMapping("/system")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> sendSystemNotification(@RequestBody Notification notification) {
        return ResponseEntity.ok(Map.of(
                "success", notificationService.save(notification),
                "message", "系统通知发送成功"));
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getUserNotifications() {
        return ResponseEntity.ok(notificationService.getCurrentUserNotifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> get(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getById(id));
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
}
