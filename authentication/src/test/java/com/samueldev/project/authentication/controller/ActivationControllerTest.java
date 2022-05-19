package com.samueldev.project.authentication.controller;

import com.samueldev.project.user.model.Activation;
import com.samueldev.project.user.service.ActivationService;
import com.samueldev.project.user.util.test.creator.ActivationCreator;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ActivationControllerTest {

    @Mock
    private ActivationService activationServiceMock;

    @InjectMocks
    private ActivationController activationController;

    private static final Activation validActivation = ActivationCreator.createValidActivation();

    @BeforeEach
    void setup() {
        when(activationServiceMock.findAll())
                .thenReturn(List.of(validActivation));

        when(activationServiceMock.findById(any(UUID.class)))
                .thenReturn(validActivation);
    }

    @Test
    void findAll_ReturnsOneActivation_WhenSuccessful() {

        ResponseEntity<List<Activation>> findAll = activationController.findAll();

        assertThat(findAll)
                .isNotNull();

        assertStatusCodeIs(findAll, HttpStatus.OK);

        assertThat(findAll.getBody())
                .isNotEmpty()
                .isNotNull()
                .isEqualTo(List.of(validActivation));
    }

    @Test
    void findById_ReturnsActivation_WhenSuccessful() {
        ResponseEntity<Activation> findById = activationController.findById(validActivation.getId());


        assertThat(findById)
                .isNotNull();

        assertStatusCodeIs(findById, HttpStatus.OK);

        assertThat(findById.getBody())
                .isNotNull()
                .isEqualTo(validActivation);
    }

    private void assertStatusCodeIs(ResponseEntity<?> responseEntity, HttpStatus status) {

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(status);
    }
}