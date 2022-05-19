package com.samueldev.project.user.mapper;

import com.samueldev.project.user.model.Activation;
import com.samueldev.project.user.model.User;
import com.samueldev.project.user.request.ActivationEmailRequestBody;

import java.util.UUID;

public class UserMapper {

    private UserMapper() {
    }

    private static final String BASE_URL = "http://localhost:8082/v1/user";
    private static final String ACTIVATION_URL = BASE_URL + "/activate?activation=%s";

    public static ActivationEmailRequestBody toActivationEmail(User user, Activation activation) {

        return ActivationEmailRequestBody.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .to(user.getEmail())
                .activationLink(generateUrl(activation.getId()))
                .build();
    }

    private static String generateUrl(UUID activationCode) {
        return String.format(ACTIVATION_URL, activationCode);
    }
}
