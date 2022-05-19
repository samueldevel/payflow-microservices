package com.samueldev.project.user.client;

import com.samueldev.project.user.request.ActivationEmailRequestBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class EmailClient {
    private static final String BASE_URL = "http://localhost:8084";
    private static final String SEND_ACTIVATION_EMAIL_GET = "/v1/email";
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void sendActivationEmail(ActivationEmailRequestBody activationEmailRequestBody) {
        log.info("sending activation email");
        ResponseEntity<Void> activationEmailRequest = restTemplate.exchange(BASE_URL + SEND_ACTIVATION_EMAIL_GET,
                HttpMethod.POST,
                new HttpEntity<>(activationEmailRequestBody),
                Void.class);

        log.info("request in sendActivationEmail - {}", activationEmailRequest);

    }
}
