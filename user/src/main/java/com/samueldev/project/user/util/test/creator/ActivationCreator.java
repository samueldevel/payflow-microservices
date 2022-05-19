package com.samueldev.project.user.util.test.creator;

import com.samueldev.project.user.model.Activation;

import java.util.UUID;

public class ActivationCreator {

    public static Activation createValidActivation() {

        return createDefaultBuilder().id(UUID.randomUUID()).build();
    }

    public static Activation createActivationToBeSaved() {
        return createDefaultBuilder().build();
    }

    public static Activation.ActivationBuilder createDefaultBuilder() {

        return Activation.builder()
                .user(UserCreator.createValidUser());
    }
}