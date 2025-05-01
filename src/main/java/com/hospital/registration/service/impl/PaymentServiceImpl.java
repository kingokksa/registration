package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.hospital.registration.entity.Payment;
import com.hospital.registration.mapper.PaymentMapper;
import com.hospital.registration.service.PaymentService;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {
}
