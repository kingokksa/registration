package com.hospital.registration.controller;

import com.hospital.registration.pojo.Payment;
import com.hospital.registration.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<Payment>> list() {
        return ResponseEntity.ok(paymentService.list());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR') or @securityService.isOwnPayment(#id)")
    public ResponseEntity<Payment> get(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody Payment payment) {
        return ResponseEntity.ok(Map.of(
                "success", paymentService.save(payment),
                "message", "支付记录创建成功"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Payment payment) {
        payment.setPaymentId(id);
        return ResponseEntity.ok(Map.of(
                "success", paymentService.updateById(payment),
                "message", "支付记录更新成功"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of(
                "success", paymentService.removeById(id),
                "message", "支付记录删除成功"));
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Payment payment) {
        boolean success = paymentService.processPayment(payment);
        String message = success ? "支付成功" : "支付失败";
        return ResponseEntity.ok(Map.of(
                "success", success,
                "message", message));
    }
}
