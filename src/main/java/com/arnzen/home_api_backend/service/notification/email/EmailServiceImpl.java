package com.arnzen.home_api_backend.service.notification.email;

import com.arnzen.home_api_backend.model.base.EmailConfirmationEntity;
import com.arnzen.home_api_backend.model.base.UserEntity;
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
            System.out.println(e.toString());
            System.out.println("There was an error sending the email.");
        }
    }

    @Override
    public EmailSetup emailSetup(String toAddress, String ccAddress, String subject, String body, boolean isHtml) {
        return new EmailSetup(toAddress, ccAddress, subject, body, isHtml);
    }

    @Override
    public void sendUserRegistrationEmail(UserEntity newUser) {
        String emailAddress = newUser.getEmail();
        String userFirstName = newUser.getFirstName();
        String subject = "Welcome to the HomeAPI Application!";
        String body = "<h1>Welcome to the App!</h1>" +
                "<div>Hi " + userFirstName + ", welcome to the HomeAPI application.</div>" +
                "<br>" +
                "<div>This email is a confirmation that your new account has been registered.</div>" +
                "<br><br>" +
                "<div>You can log into your account using the following link: <a href='http://localhost:4200'>Login</a></div>" +
                "<br>" +
                "<div>Once logged in, you will be able to confirm your email address on the home page.</div>" +
                "<br><br>" +
                "<div>Thank you for using the HomeAPI application. We hope the application helps to bring peace of mind," +
                " and allows you to better understand the environments in which you live, work, and play.</div>" +
                "<br><br>" +
                "<div>Best,</div>" +
                "<div>The HomeAPI Team</div>";

        boolean isHtml = true;

        sendEmail(emailSetup(emailAddress, null, subject, body, isHtml));
    }

    @Override
    public void sendEmailAddressConfirmationEmail(UserEntity user, int confirmationCode) {
        String emailAddress = user.getEmail();
        String userFirstName = user.getFirstName();
        String subject = "Email Confirmation";
        String body = "<h1>Email Confirmation</h1>" +
                "<div>Hi " + userFirstName + ", this email contains an email confirmation code to verify your email with the HomeAPI application.</div>" +
                "<br>" +
                "<div>Please enter the confirmation code in the field provided in the HomeAPI application.</div>" +
                "<br><br>" +
                "<div>Confirmation Code: " + confirmationCode + "</div>" +
                "<br>" +
                "<div>You have five minutes from the point the confirmation code was generated to enter the code and confirm your email address.</div>" +
                "<br><br>" +
                "<div>If you experience any errors in the confirmation process, simply press the 'Confirm Email' button again to generate a new confirmation code.</div>" +
                "<br><br>" +
                "<div>Best,</div>" +
                "<div>The HomeAPI Team</div>";

        boolean isHtml = true;

        sendEmail(emailSetup(emailAddress, null, subject, body, isHtml));
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
