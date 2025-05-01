package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.hospital.registration.entity.Notification;
import com.hospital.registration.mapper.NotificationMapper;
import com.hospital.registration.service.NotificationService;

@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification>
                implements NotificationService {
}
