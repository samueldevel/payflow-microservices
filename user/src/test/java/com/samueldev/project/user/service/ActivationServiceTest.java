package com.samueldev.project.user.service;

import com.samueldev.project.user.client.EmailClient;
import com.samueldev.project.user.model.Activation;
import com.samueldev.project.user.model.User;
import com.samueldev.project.user.repository.ActivationRepository;
import com.samueldev.project.user.request.ActivationEmailRequestBody;
import com.samueldev.project.user.util.test.creator.ActivationCreator;
import com.samueldev.project.user.util.test.creator.UserCreator;
import com.samueldev.project.user.util.test.setup.AssertUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ActivationServiceTest extends AssertUtils {

    @Mock
    private ActivationRepository activationRepositoryMock;

    @InjectMocks
    private ActivationService activationService;

    private static final User validUser = UserCreator.createValidUser();

    private static final Activation validActivation = ActivationCreator.createValidActivation();

    @BeforeEach
    public void setUp() {

        when(activationRepositoryMock.findAll())
                .thenReturn(List.of(validActivation));

        when(activationRepositoryMock.findById(any(UUID.class)))
                .thenReturn(Optional.of(validActivation));

        when(activationRepositoryMock.save(any(Activation.class)))
                .thenReturn(validActivation);

        when(activationRepositoryMock.findByUser(any(User.class)))
                .thenReturn(Optional.of(validActivation));

        doNothing().when(activationRepositoryMock).delete(any(Activation.class));
    }

    @Test
    void findAll_ReturnListOfOneActivation_WhenSuccessful() {
        List<Activation> allActivations = activationService.findAll();

        assertThat(allActivations)
                .hasSize(1)
                .containsOnly(validActivation);

        assertFields(allActivations.get(0), validActivation);
    }

    @Test
    void findById_ReturnsActivationFound_WhenSuccessful() {
        Activation activationFound = activationService.findById(UUID.randomUUID());

        assertFields(activationFound, validActivation);
    }

    @Test
    void saveActivation_ReturnsActivationSaved_WhenSuccessful() {
        Activation activation = activationService.saveActivation(validActivation);

        assertFields(activation, validActivation);
    }

    @Test
    void sendActivationEmail_DoesNotThrowException_WhenSuccessful() {
        try (MockedStatic<EmailClient> util = mockStatic(EmailClient.class)) {
            util.when(() -> EmailClient.sendActivationEmail(any(ActivationEmailRequestBody.class)))
                    .thenAnswer(invocation -> null);

            assertThatCode(() -> activationService.sendActivationEmail(validUser))
                    .doesNotThrowAnyException();
        }
    }

    @Test
    void deleteUserWithActivationId_RemoveUserFromRepository_WhenSuccessful() {

        assertThatCode(() -> activationService.deleteUserWithActivationId(validUser))
                .doesNotThrowAnyException();
    }

    // Flamed Cases

    @Test
    void findById_ThrowsRuntimeException_WhenActivationNotFound() {
        when(activationRepositoryMock.findById(any(UUID.class)))
                .thenReturn(Optional.empty());
        UUID uuid = UUID.randomUUID();

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> activationService.findById(uuid))
                .withMessage("Id not found");
    }

    @Test
    void deleteUserWithActivationId_ThrowRuntimeException_WhenUserNotFound() {
        when(activationRepositoryMock.findByUser(any(User.class)))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> activationService.deleteUserWithActivationId(validUser))
                .withMessage("User used to delete activation does not found!");
    }
}
