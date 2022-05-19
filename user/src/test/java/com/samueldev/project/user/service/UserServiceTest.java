package com.samueldev.project.user.service;

import com.samueldev.project.user.model.Activation;
import com.samueldev.project.user.model.User;
import com.samueldev.project.user.repository.UserRepository;
import com.samueldev.project.user.request.SignUpRequestBody;
import com.samueldev.project.user.response.SignUpResponseBody;
import com.samueldev.project.user.util.test.creator.ActivationCreator;
import com.samueldev.project.user.util.test.creator.SignUpRequestBodyCreator;
import com.samueldev.project.user.util.test.creator.SignUpResponseBodyCreator;
import com.samueldev.project.user.util.test.creator.UserCreator;
import com.samueldev.project.user.util.test.setup.AssertUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Log4j2
class UserServiceTest extends AssertUtils {
    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private ActivationService activationServiceMock;

    @Mock
    private BCryptPasswordEncoder encoderMock;

    @InjectMocks
    private UserService userService;

    private static final User validUser = UserCreator.createValidUser();

    private static final Activation validActivation = ActivationCreator.createValidActivation();
    private static final SignUpRequestBody validSignUpRequestBody = SignUpRequestBodyCreator.createValidBody();


    @BeforeEach
    void setUpUserRepository() {

        when(userRepositoryMock.findAll())
                .thenReturn(List.of(validUser));

        when(userRepositoryMock.findById(anyLong()))
                .thenReturn(Optional.of(validUser));

        when(userRepositoryMock.save(any(User.class)))
                .thenReturn(validUser);

        doNothing().when(userRepositoryMock).delete(any(User.class));
    }

    @BeforeEach
    void setUpActivationService() {
        doNothing().when(activationServiceMock).sendActivationEmail(any(User.class));
        doNothing().when(activationServiceMock).deleteUserWithActivationId(any(User.class));

        when(activationServiceMock.findById(any(UUID.class)))
                .thenReturn(validActivation);
    }

    @BeforeEach
    void setUpBcryptEncoder() {

        when(encoderMock.encode(anyString()))
                .thenReturn("encrypted password");
    }

    @Test
    void findAllUsers_ReturnsListOfOneUser_WhenSuccessful() {
        List<User> allUsers = userService.findAllUsers();

        assertThat(allUsers)
                .hasSize(1)
                .containsOnly(validUser);
    }

    @Test
    void findById_ReturnsUserFound_WhenSuccessful() {
        User userFound = userService.findById(1L);

        assertFields(userFound, validUser);
    }

    @Test
    void save_PersistUser_WhenSuccessful() {
        User user = userService.saveUser(validUser);

        assertFields(user, validUser);
    }

    @Test
    void signUpUser_ReturnsSignUpRequestBody_WhenSuccessful() {
        SignUpResponseBody signUpResponseBody = userService.signUpUser(validSignUpRequestBody);

        assertFields(signUpResponseBody, SignUpResponseBodyCreator.createValidBody());
    }

    @Test
    void unlockUser_ConfirmUser_WhenActivationCodeIsCorrectly() {

        assertThatCode(() -> userService.unlockUser(UUID.randomUUID()))
                .doesNotThrowAnyException();
    }

    @Test
    void deleteUser_RemovesUser_WhenSuccessful() {

        assertThatCode(() -> userService.deleteUser(1L))
                .doesNotThrowAnyException();
    }

    // Flamed Cases

    @Test
    void findById_ThrowsRuntimeException_WhenUserIsNotFound() {
        when(userRepositoryMock.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> userService.findById(1L))
                .withMessage(String.format("User with id %s does not found!", 1L));
    }
}
