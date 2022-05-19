package com.samueldev.project.user.mapper;

import com.samueldev.project.user.model.User;
import com.samueldev.project.user.request.SignUpRequestBody;

public class SignUpMapper {

    private SignUpMapper() {
    }

    public static User toUser(SignUpRequestBody signUpRequestBody) {
        return User.builder()
                .email(signUpRequestBody.getEmail())
                .firstName(signUpRequestBody.getFirstName())
                .lastName(signUpRequestBody.getLastName())
                .role(signUpRequestBody.getRole())
                .username(signUpRequestBody.getUsername())
                .password(signUpRequestBody.getPassword())
                .build();
    }
}
