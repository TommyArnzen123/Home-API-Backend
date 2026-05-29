package com.arnzen.home_api_backend.service.notification.email;

import com.arnzen.home_api_backend.model.notification.EmailSetup;

public interface EmailService {
    public EmailSetup emailSetup(String toAddress, String ccAddress, String subject, String body, boolean isHtml);
    public void sendEmail(EmailSetup emailSetup);
    public void sendTemperatureThresholdViolationEmail(
            Double temperature, Double minimumThreshold,
            Double maximumThreshold, String homeName, String locationName,
            String ownerFirstName, String ownerEmailAddress);
}
