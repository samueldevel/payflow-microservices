package com.samueldev.project.user.util.test.creator;

import com.samueldev.project.user.response.SignUpResponseBody;
import org.springframework.http.HttpStatus;

public class SignUpResponseBodyCreator {

    public static SignUpResponseBody createValidBody() {

        return createDefaultSignUpBuilder()
                .build();
    }

    private static SignUpResponseBody.SignUpResponseBodyBuilder createDefaultSignUpBuilder() {
        final String SIGN_UP_SUCCESSFULLY_MSG =
                "Congratulations, your account was successfully saved in our database," +
                        " we will need which you confirm account in your email";

        return SignUpResponseBody.builder()
                .status(HttpStatus.OK.value())
                .message(SIGN_UP_SUCCESSFULLY_MSG)
                .username("user")
                .email("samuel.elias.dev@gmail.com");
    }
}
