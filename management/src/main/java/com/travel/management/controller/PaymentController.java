package com.travel.management.controller;

import com.travel.management.model.Payment;
import com.travel.management.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;
    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public Payment makePayment(@RequestParam Long bookingId, @RequestParam Double amount) {
        return paymentService.processPayment(bookingId, amount);
    }
}

