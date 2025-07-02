package com.acikgozkaan.payment_service.service;

import com.acikgozkaan.payment_service.dto.PaymentCompletedEvent;
import com.acikgozkaan.payment_service.dto.PaymentFailedEvent;
import com.acikgozkaan.payment_service.entity.Payment;
import com.acikgozkaan.payment_service.producer.PaymentProducer;
import com.acikgozkaan.payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;
    private final Random random = new Random();

    public PaymentService(PaymentRepository paymentRepository, PaymentProducer paymentProducer) {
        this.paymentRepository = paymentRepository;
        this.paymentProducer = paymentProducer;
    }

    public void processPayment(String orderId) {
        boolean isPaid = random.nextBoolean(); // Randomly returns true or false

        paymentRepository.save(new Payment(orderId, isPaid));

        if (isPaid) {
            paymentProducer.sendPaymentCompleted(
                    new PaymentCompletedEvent(orderId, "💳 Ödeme başarılı.")
            );
        } else {
            paymentProducer.sendPaymentFailed(
                    new PaymentFailedEvent(orderId, "❌ Ödeme başarısız.")
            );
        }

    }
}