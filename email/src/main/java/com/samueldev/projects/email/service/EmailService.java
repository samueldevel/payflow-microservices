package com.samueldev.projects.email.service;

import com.samueldev.projects.email.request.ActivationEmailRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendActivationEmail(ActivationEmailRequestBody emailRequestBody) {
        SimpleMailMessage templateMessage = new SimpleMailMessage();

        SimpleMailMessage msg = new SimpleMailMessage(templateMessage);
        String thanks = String.format("Dear %s %s, first of all we want to thank you for registering on our app " +
                "%n We promise a great experience, and also our support contact, %s%n%n", emailRequestBody.getFirstName(), emailRequestBody.getLastName(), "http://localhost:8082/payflow/contact-us");

        String activationMessage = String.format("you will need to activate your account accessing this link" +
                "%n %s %n%n", emailRequestBody.getActivationLink());

        String byebye = "Thank you time, By our beautiful company PayFlow \uD83E\uDDE1\uD83E\uDDE1\uD83E\uDDE1";

        final String message = thanks + activationMessage + byebye;

        msg.setSubject("Activation Link to PayFlow");
        msg.setTo(emailRequestBody.getTo());
        msg.setText(message);

        this.mailSender.send(msg);
    }
}
