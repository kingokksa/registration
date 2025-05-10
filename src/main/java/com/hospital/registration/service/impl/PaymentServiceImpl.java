package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.registration.pojo.Payment;
import com.hospital.registration.pojo.Appointment;
import com.hospital.registration.mapper.PaymentMapper;
import com.hospital.registration.service.PaymentService;
import com.hospital.registration.service.AppointmentService;
import com.hospital.registration.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private NotificationService notificationService;

    @Override
    @Transactional
    public Map<String, Object> createPayment(Payment payment) {
        payment.setCreatedAt(LocalDateTime.now());
        payment.setPaymentStatus("PENDING");
        boolean success = save(payment);

        if (success) {
            Appointment appointment = appointmentService.getById(payment.getAppointmentId());
            if (appointment != null) {
                notificationService.sendUserNotification(
                        appointment.getPatientId(),
                        "支付已创建，请及时完成支付",
                        "PAYMENT");
            }
        }

        return Map.of(
                "success", success,
                "message", success ? "支付创建成功" : "支付创建失败");
    }

    @Override
    public List<Payment> getUserPayments(Long userId) {
        QueryWrapper<Payment> wrapper = new QueryWrapper<>();
        wrapper.inSql("appointment_id",
                "SELECT appointment_id FROM Appointments WHERE patient_id = " + userId)
                .orderByDesc("created_at");
        return list(wrapper);
    }

    @Override
    public Payment getAppointmentPayment(Long appointmentId) {
        QueryWrapper<Payment> wrapper = new QueryWrapper<>();
        wrapper.eq("appointment_id", appointmentId);
        return getOne(wrapper);
    }

    @Override
    @Transactional
    public Map<String, Object> updatePaymentStatus(Long paymentId, String status) {
        Payment payment = getById(paymentId);
        if (payment == null) {
            return Map.of("success", false, "message", "支付记录不存在");
        }

        payment.setPaymentStatus(status);
        boolean success = updateById(payment);

        if (success && "PAID".equals(status)) {
            Appointment appointment = appointmentService.getById(payment.getAppointmentId());
            if (appointment != null) {
                notificationService.sendUserNotification(
                        appointment.getPatientId(),
                        "支付已完成",
                        "PAYMENT");
            }
        }

        return Map.of(
                "success", success,
                "message", success ? "支付状态更新成功" : "支付状态更新失败");
    }

    @Override
    @Transactional
    public Map<String, Object> refundPayment(Long paymentId) {
        Payment payment = getById(paymentId);
        if (payment == null) {
            return Map.of("success", false, "message", "支付记录不存在");
        }

        payment.setPaymentStatus("REFUNDED");
        boolean success = updateById(payment);

        if (success) {
            Appointment appointment = appointmentService.getById(payment.getAppointmentId());
            if (appointment != null) {
                notificationService.sendUserNotification(
                        appointment.getPatientId(),
                        "退款已完成",
                        "PAYMENT");
            }
        }

        return Map.of(
                "success", success,
                "message", success ? "退款处理成功" : "退款处理失败");
    }

    @Override
    public Map<String, Object> getPaymentStatistics() {
        // 实现支付统计逻辑
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalPayments", count());

        QueryWrapper<Payment> paidWrapper = new QueryWrapper<>();
        paidWrapper.eq("payment_status", "PAID");
        stats.put("paidPayments", count(paidWrapper));

        QueryWrapper<Payment> pendingWrapper = new QueryWrapper<>();
        pendingWrapper.eq("payment_status", "PENDING");
        stats.put("pendingPayments", count(pendingWrapper));

        QueryWrapper<Payment> refundedWrapper = new QueryWrapper<>();
        refundedWrapper.eq("payment_status", "REFUNDED");
        stats.put("refundedPayments", count(refundedWrapper));

        return stats;
    }
}
