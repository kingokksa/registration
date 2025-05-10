package com.hospital.registration.service;

import com.hospital.registration.pojo.Appointment;
import com.hospital.registration.pojo.Payment;
import com.hospital.registration.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    public boolean isOwnPayment(Long paymentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByUsername(username);

        Payment payment = paymentService.getById(paymentId);
        if (payment == null || currentUser == null) {
            return false;
        }

        // 通过appointment关联检查payment是否属于当前用户
        Appointment appointment = appointmentService.getById(payment.getAppointmentId());
        return appointment != null && appointment.getPatientId().equals(currentUser.getUserId());
    }

    public boolean isOwnAppointment(Long appointmentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByUsername(username);

        Appointment appointment = appointmentService.getById(appointmentId);
        return appointment != null && appointment.getPatientId().equals(currentUser.getUserId());
    }

    public boolean isAppointmentDoctor(Long appointmentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByUsername(username);

        Appointment appointment = appointmentService.getById(appointmentId);
        return appointment != null && appointment.getDoctorId().equals(currentUser.getUserId());
    }

    public boolean hasRole(String role) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

    public boolean isCurrentUser(Long userId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByUsername(username);
        return currentUser != null && currentUser.getUserId().equals(userId);
    }
}
