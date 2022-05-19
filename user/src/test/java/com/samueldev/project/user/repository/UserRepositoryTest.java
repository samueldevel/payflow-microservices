package com.samueldev.project.user.repository;

import com.samueldev.project.user.model.User;
import com.samueldev.project.user.util.test.creator.UserCreator;
import com.samueldev.project.user.util.test.setup.AssertUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class UserRepositoryTest extends AssertUtils {
    @Autowired
    private UserRepository userRepository;

    private static final User VALID_USER = UserCreator.createValidUser();
    private static final User USER_TO_BE_SAVED = UserCreator.createUserToBeSaved();
    final DataTestUtil<User, Long> dataTestUtil = new DataTestUtil<>(USER_TO_BE_SAVED);

    @Test
    void findAll_ReturnsAllUsers_WhenSuccessful() {
        dataTestUtil.withEntitySaved(userRepository, userSaved -> {
            List<User> users = userRepository.findAll();

            assertThat(users)
                    .hasSize(1)
                    .contains(userSaved);

            assertFields(userSaved, VALID_USER.withId(userSaved.getId()));
        });
    }

    @Test
    void findById_ReturnsUserFound_WhenSuccessful() {
        dataTestUtil.withEntitySaved(userRepository, userSaved -> {
            User userFound = userRepository.findById(userSaved.getId())
                    .orElseThrow(() -> new RuntimeException("Id is not found"));

            assertFields(userFound, userSaved);
        });
    }

    @Test
    void save_PersistUserToBeSaved_WhenSuccessful() {
        dataTestUtil.withEntitySaved(userRepository, userSaved -> assertFields(userSaved, VALID_USER.withId(userSaved.getId())));
    }

    @Test
    void delete_RemoveUser_WhenSuccessful() {
        dataTestUtil.withEntitySaved(userRepository, userSaved -> {
            userRepository.delete(userSaved);

            assertThat(userRepository.findAll())
                    .isEmpty();
        });
    }

    // flamed cases

    @Test
    void findAll_ReturnEmptyList_WhenNoHasUser() {
        List<User> emptyList = userRepository.findAll();

        assertThat(emptyList)
                .isEmpty();
    }

    @Test
    void findById_ThrowRuntimeException_WhenUserNotFound() {
        final String MESSAGE = "User not found!";
        Optional<User> userById = userRepository.findById(1L);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> userById.orElseThrow(() -> new RuntimeException(MESSAGE)))
                .withMessage(MESSAGE);
    }

}