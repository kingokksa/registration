package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.registration.pojo.Notification;
import com.hospital.registration.mapper.NotificationMapper;
import com.hospital.registration.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification>
                implements NotificationService {
}
