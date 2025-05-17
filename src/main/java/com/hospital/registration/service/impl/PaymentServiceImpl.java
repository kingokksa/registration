package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.registration.pojo.Payment;
import com.hospital.registration.pojo.Appointment;
import com.hospital.registration.mapper.PaymentMapper;
import com.hospital.registration.service.PaymentService;
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
    private NotificationService notificationService;

    @Override
    @Transactional
    public Map<String, Object> createPayment(Payment payment) {
        payment.setCreatedAt(LocalDateTime.now());
        payment.setPaymentStatus("pending"); // pending
        boolean success = save(payment);

        if (success) {
            // 支付创建成功后，发送通知
            // 如果需要预约信息，可以在调用 createPayment 的地方处理，避免循环依赖
            notificationService.sendUserNotification(
                    // 这里需要获取 patientId，可以考虑在 Payment 对象中包含或者通过其他方式获取
                    // 为了简单，这里暂时无法发送通知给特定用户
                    // payment.getPatientId(), // Payment POJO does not have patientId
                    // "支付已创建，请及时完成支付",
                    // "PAYMENT"
                    // 暂时跳过发送通知，或者发送给一个默认用户/管理员
                    1L, // 示例用户ID，请根据实际情况修改
                    "一笔支付已创建，请及时完成支付。",
                    "general"); // Changed from PAYMENT to general
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

        if (success && "success".equals(status)) { // success
            // 支付状态更新成功后，发送通知
            // 如果需要预约信息，可以在调用 updatePaymentStatus 的地方处理，避免循环依赖
            notificationService.sendUserNotification(
                    // 这里需要获取 patientId，可以考虑在 Payment 对象中包含或者通过其他方式获取
                    // 为了简单，这里暂时无法发送通知给特定用户
                    // payment.getPatientId(), // Payment POJO does not have patientId
                    // "支付已完成",
                    // "PAYMENT"
                    // 暂时跳过发送通知，或者发送给一个默认用户/管理员
                    1L, // 示例用户ID，请根据实际情况修改
                    "一笔支付状态已更新为已完成。",
                    "general"); // Changed from PAYMENT to general
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

        payment.setPaymentStatus("failed"); // failed (Assuming refund maps to failed status in this simple model)
        boolean success = updateById(payment);

        if (success) {
            // 退款处理成功后，发送通知
            // 如果需要预约信息，可以在调用 refundPayment 的地方处理，避免循环依赖
            notificationService.sendUserNotification(
                    // 这里需要获取 patientId，可以考虑在 Payment 对象中包含或者通过其他方式获取
                    // 为了简单，这里暂时无法发送通知给特定用户
                    // payment.getPatientId(), // Payment POJO does not have patientId
                    // "退款已完成",
                    // "PAYMENT"
                    // 暂时跳过发送通知，或者发送给一个默认用户/管理员
                    1L, // 示例用户ID，请根据实际情况修改
                    "一笔退款已完成。",
                    "general"); // Changed from PAYMENT to general
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
        paidWrapper.eq("payment_status", "success"); // success
        stats.put("paidPayments", count(paidWrapper));

        QueryWrapper<Payment> pendingWrapper = new QueryWrapper<>();
        pendingWrapper.eq("payment_status", "pending"); // pending
        stats.put("pendingPayments", count(pendingWrapper));

        QueryWrapper<Payment> refundedWrapper = new QueryWrapper<>();
        refundedWrapper.eq("payment_status", "failed"); // failed (Assuming refund maps to failed status)
        stats.put("refundedPayments", count(refundedWrapper));

        return stats;
    }

    @Override
    @Transactional
    public boolean processPayment(Payment payment) {
        // 模拟支付处理逻辑
        // 在实际应用中，这里会调用第三方支付接口
        System.out.println("模拟支付处理中...");
        System.out.println("支付金额: " + payment.getAmount());
        System.out.println("支付方式: " + payment.getPaymentMethod());

        // 根据 appointmentId 查找支付记录
        QueryWrapper<Payment> wrapper = new QueryWrapper<>();
        wrapper.eq("appointment_id", payment.getAppointmentId());
        Payment existingPayment = getOne(wrapper);

        if (existingPayment == null) {
            System.out.println("未找到对应的支付记录");
            return false;
        }

        // 模拟支付成功，更新支付状态
        existingPayment.setPaymentStatus("success"); // success
        boolean success = updateById(existingPayment);

        if (success) {
            // 支付成功后，发送通知
            // 如果需要更新预约状态，可以在调用 processPayment 的地方处理，避免循环依赖
            notificationService.sendUserNotification(
                    // 这里需要获取 patientId，可以考虑在 Payment 对象中包含或者通过其他方式获取
                    // 为了简单，这里暂时无法发送通知给特定用户
                    // existingPayment.getPatientId(), // Payment POJO does not have patientId
                    // "您的支付已成功完成。",
                    // "PAYMENT"
                    // 暂时跳过发送通知，或者发送给一个默认用户/管理员
                    1L, // 示例用户ID，请根据实际情况修改
                    "一笔支付已成功完成。",
                    "general"); // Changed from PAYMENT to general
        }

        return success;
    }
}
