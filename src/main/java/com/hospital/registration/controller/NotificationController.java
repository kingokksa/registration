package com.hospital.registration.controller;

import com.hospital.registration.pojo.Notification;
import com.hospital.registration.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

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
