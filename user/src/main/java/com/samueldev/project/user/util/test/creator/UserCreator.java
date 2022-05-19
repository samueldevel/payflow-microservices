package com.samueldev.project.user.util.test.creator;

import com.samueldev.project.user.model.User;
import com.samueldev.project.user.role.SecurityRole;

public class UserCreator {

    private UserCreator() {
    }

    public static User createValidUser() {
        return createDefaultUserBuilder().id(1L).build();
    }

    public static User createUserToBeSaved() {
        return createDefaultUserBuilder().build();
    }

    private static User.UserBuilder createDefaultUserBuilder() {
        return User.builder()
                .username("user")
                .password("password")
                .firstName("User")
                .lastName("User")
                .email("samuel.elias.dev@gmail.com")
                .role(SecurityRole.USER)
                .enabled(true)
                .locked(true);
    }
}
