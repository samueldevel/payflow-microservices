package com.samueldev.project.authentication.controller;

import com.samueldev.project.user.model.User;
import com.samueldev.project.user.request.SignUpRequestBody;
import com.samueldev.project.user.response.SignUpResponseBody;
import com.samueldev.project.user.service.UserService;
import com.samueldev.project.user.util.test.creator.SignUpRequestBodyCreator;
import com.samueldev.project.user.util.test.creator.UserCreator;
import com.samueldev.project.user.util.test.setup.AssertUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserControllerTest extends AssertUtils {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private static final User validUser = UserCreator.createValidUser();

    private static final User userToBeSaved = UserCreator.createUserToBeSaved();

    private static final SignUpResponseBody signUpResponseBody = SignUpResponseBody.buildWithUser(validUser);

    @BeforeEach
    void setup() {

        when(userService.findAllUsers())
                .thenReturn(List.of(validUser));

        when(userService.signUpUser(any(SignUpRequestBody.class)))
                .thenReturn(signUpResponseBody);

        doNothing().when(userService).unlockUser(any(UUID.class));

        doNothing().when(userService).deleteUser(anyLong());
    }

    @Test
    void listAllUsers_ReturnListOfOneUser_WhenSuccessful() {
        ResponseEntity<List<User>> allUsers = userController.listAllUsers();

        assertThat(allUsers)
                .isNotNull();

        assertStatusCodeIs(allUsers, HttpStatus.OK);

        assertThat(allUsers.getBody())
                .isNotEmpty()
                .isNotNull()
                .contains(validUser);

        assertFields(allUsers.getBody().get(0), validUser);
    }

    @Test
    void signUpUser_ReturnsSignUpResponseBody_WhenSuccessful() {
        ResponseEntity<SignUpResponseBody> signUpUser = userController.signUpUser(SignUpRequestBodyCreator.createValidBody());

        assertStatusCodeIs(signUpUser, HttpStatus.CREATED);

        assertThat(signUpUser.getBody())
                .isNotNull();

        assertFields(signUpUser.getBody(), signUpResponseBody);
    }

    @Test
    void activateUser_DoesNotThrowException_WhenSuccessful() {
        assertThatCode(() -> {
            ResponseEntity<Void> activatedUser = userController.activateUser(UUID.randomUUID());

            assertStatusCodeIs(activatedUser, HttpStatus.OK);
        }).doesNotThrowAnyException();
    }

    @Test
    void deleteUser_ReturnsSuccessfullyMessage_WhenSuccessful() {
        final String message = "User with id %s was successfully deleted";

        ResponseEntity<String> deletedUser = userController.deleteUser(validUser.getId());

        assertStatusCodeIs(deletedUser, HttpStatus.NO_CONTENT);

        assertThat(deletedUser)
                .isNotNull();

        assertThat(deletedUser.getBody())
                .isNotNull()
                .isEqualTo(String.format(message, validUser.getId()));
    }

    private void assertStatusCodeIs(ResponseEntity<?> responseEntity, HttpStatus status) {

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(status);
    }
}