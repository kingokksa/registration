package com.hospital.registration.controller;

import org.springframework.web.bind.annotation.*;
import com.hospital.registration.entity.Payment;
import com.hospital.registration.service.PaymentService;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> list() {
        return paymentService.list();
    }

    @GetMapping("/{id}")
    public Payment get(@PathVariable Long id) {
        return paymentService.getById(id);
    }

    @PostMapping
    public boolean save(@RequestBody Payment payment) {
        return paymentService.save(payment);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Long id, @RequestBody Payment payment) {
        payment.setPaymentId(id);
        return paymentService.updateById(payment);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return paymentService.removeById(id);
    }
}
