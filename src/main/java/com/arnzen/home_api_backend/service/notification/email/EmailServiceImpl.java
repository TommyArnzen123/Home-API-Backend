package com.arnzen.home_api_backend.service.notification.email;

import com.arnzen.home_api_backend.model.notification.EmailSetup;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private final String sendingEmailAccount;

    public EmailServiceImpl(@Value("${spring.mail.username}") String sendingEmail) {
        this.sendingEmailAccount = sendingEmail;
    }

    @Override
    public void sendEmail(EmailSetup emailSetup) {
        try {
            MimeMessage mimeMessage =
                   javaMailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            messageHelper.setFrom("HomeAPI <" + sendingEmailAccount + ">");
            messageHelper.setTo(emailSetup.getToAddress());
            messageHelper.setSubject(emailSetup.getSubject());
            messageHelper.setText(emailSetup.getBody(), emailSetup.isHtml());

            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            System.out.println("There was an error sending the email.");
        }
    }

    @Override
    public EmailSetup emailSetup(String toAddress, String ccAddress, String subject, String body, boolean isHtml) {
        return new EmailSetup(toAddress, ccAddress, subject, body, isHtml);
    }

    @Override
    public void sendTemperatureThresholdViolationEmail(
            Double temperature,
            Double minimumThreshold,
            Double maximumThreshold,
            String homeName,
            String locationName,
            String ownerFirstName,
            String ownerEmailAddress) {

        String minimumThresholdValue = minimumThreshold != null ? minimumThreshold.toString() : "Not Set";
        String maximumThresholdValue = maximumThreshold != null ? maximumThreshold.toString() : "Not Set";


        String subject = "HomeAPI Temperature Threshold Violation";
        String body = "<h1>Temperature Threshold Violation</h1>" +
                "<div>Hi " + ownerFirstName + ", your temperature threshold has been violated.</div>" +
                "<div>Home: " + homeName + "</div>" +
                "<div>Location: " + locationName + "</div>" +
                "<div>Minimum Allowed Temperature: " + minimumThresholdValue + "</div>" +
                "<div>Maximum Allowed Temperature: " + maximumThresholdValue + "</div>" +
                "<div>Current Temperature: " + temperature + "</div>";
        boolean isHtml = true;

        sendEmail(emailSetup(ownerEmailAddress, null, subject, body, isHtml));
    }
}
