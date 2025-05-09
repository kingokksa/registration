package com.hospital.registration.controller;

import com.hospital.registration.pojo.Payment;
import com.hospital.registration.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

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
