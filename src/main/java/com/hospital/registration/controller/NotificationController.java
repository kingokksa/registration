package com.hospital.registration.controller;

import org.springframework.web.bind.annotation.*;
import com.hospital.registration.entity.Notification;
import com.hospital.registration.service.NotificationService;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> list() {
        return notificationService.list();
    }

    @GetMapping("/{id}")
    public Notification get(@PathVariable Long id) {
        return notificationService.getById(id);
    }

    @PostMapping
    public boolean save(@RequestBody Notification notification) {
        return notificationService.save(notification);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Long id, @RequestBody Notification notification) {
        notification.setNotificationId(id);
        return notificationService.updateById(notification);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return notificationService.removeById(id);
    }
}
