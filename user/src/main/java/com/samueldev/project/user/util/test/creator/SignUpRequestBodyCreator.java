package com.samueldev.project.user.util.test.creator;

import com.samueldev.project.user.request.SignUpRequestBody;
import com.samueldev.project.user.role.SecurityRole;

public class SignUpRequestBodyCreator {

    public static SignUpRequestBody createValidBody() {

        return createDefaultSignUpBuilder()
                .build();
    }

    private static SignUpRequestBody.SignUpRequestBodyBuilder createDefaultSignUpBuilder() {
        return SignUpRequestBody.builder()
                .username("user")
                .password("password")
                .firstName("User")
                .lastName("User")
                .email("samuel.elias.dev@gmail.com")
                .role(SecurityRole.USER);
    }
}
