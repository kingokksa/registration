package com.hospital.registration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.registration.pojo.Payment;
import java.util.List;
import java.util.Map;

public interface PaymentService extends IService<Payment> {
    /**
     * 创建支付记录
     */
    Map<String, Object> createPayment(Payment payment);

    /**
     * 获取用户的支付记录
     */
    List<Payment> getUserPayments(Long userId);

    /**
     * 获取预约相关的支付记录
     */
    Payment getAppointmentPayment(Long appointmentId);

    /**
     * 更新支付状态
     */
    Map<String, Object> updatePaymentStatus(Long paymentId, String status);

    /**
     * 退款处理
     */
    Map<String, Object> refundPayment(Long paymentId);

    /**
     * 获取支付统计
     */
    Map<String, Object> getPaymentStatistics();

    /**
     * 模拟支付处理
     */
    boolean processPayment(Payment payment);
}
