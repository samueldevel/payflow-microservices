package com.samueldev.project.user.repository;

import com.samueldev.project.user.model.Activation;
import com.samueldev.project.user.model.User;
import com.samueldev.project.user.util.test.creator.ActivationCreator;
import com.samueldev.project.user.util.test.creator.UserCreator;
import com.samueldev.project.user.util.test.setup.AssertUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest()
class ActivationRepositoryTest extends AssertUtils {

    @Autowired
    private ActivationRepository activationRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Activation VALID_ACTIVATION = ActivationCreator.createValidActivation();
    private static final Activation ACTIVATION_TO_BE_SAVED = ActivationCreator.createActivationToBeSaved();


    DataTestUtil<Activation, UUID> dataTestUtil;
    User userSetUp;

    @BeforeEach
    void setUp() {
        userSetUp = userRepository.save(UserCreator.createUserToBeSaved());
        dataTestUtil = new DataTestUtil<>(ACTIVATION_TO_BE_SAVED.withUser(userSetUp));
    }

    @Test
    void findAll_ReturnsAllActivations_WhenSuccessful() {
        dataTestUtil.withEntitySaved(activationRepository, activationSaved -> {
            List<Activation> activations = activationRepository.findAll();

            assertThat(activations)
                    .hasSize(1)
                    .contains(activationSaved);

            Activation activation = VALID_ACTIVATION.withId(activationSaved.getId());
            activation.getUser().setId(userSetUp.getId());

            assertFields(activationSaved, activation);
        });
    }

    @Test
    void findById_ReturnsActivationFound_WhenSuccessful() {
        dataTestUtil.withEntitySaved(activationRepository, activationSaved -> {
            Activation activationFound = activationRepository.findById(activationSaved.getId())
                    .orElseThrow(() -> new RuntimeException("Id is not found"));

            assertFields(activationFound, activationSaved);
        });
    }

    @Test
    void findByUser_ReturnsActivation_WhenSuccessful() {
        dataTestUtil.withEntitySaved(activationRepository, activationSaved -> {
            Activation activationFound = activationRepository.findByUser(userSetUp)
                    .orElseThrow(() -> new RuntimeException("User not found!"));

            assertFields(activationFound, activationSaved);
        });
    }

    @Test
    void save_PersistActivationToBeSaved_WhenSuccessful() {
        dataTestUtil.withEntitySaved(activationRepository, activationSaved -> {

            Activation activation = VALID_ACTIVATION.withId(activationSaved.getId());
            activation.getUser().setId(userSetUp.getId());

            assertFields(activationSaved, activation);
        });
    }

    @Test
    void delete_RemoveActivation_WhenSuccessful() {
        dataTestUtil.withEntitySaved(activationRepository, activationSaved -> {
            activationRepository.delete(activationSaved);

            assertThat(activationRepository.findAll())
                    .isEmpty();
        });
    }

    // flamed cases

    @Test
    void findAll_ReturnEmptyList_WhenNoHasActivation() {
        List<Activation> emptyList = activationRepository.findAll();

        assertThat(emptyList)
                .isEmpty();
    }

    @Test
    void findById_ThrowRuntimeException_WhenActivationNotFound() {
        final String MESSAGE = "Activation not found!";
        Optional<Activation> activationById = activationRepository.findById(UUID.randomUUID());

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> activationById.orElseThrow(() -> new RuntimeException(MESSAGE)))
                .withMessage(MESSAGE);
    }

    @Test
    void findByUser_ThrowRuntimeException_WhenActivationDoesNotFound() {
        final String MESSAGE = "Activation not found!";

        Optional<Activation> activationFound = activationRepository.findByUser(userSetUp);


        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> activationFound.orElseThrow(() -> new RuntimeException(MESSAGE)))
                .withMessage(MESSAGE);
    }

}