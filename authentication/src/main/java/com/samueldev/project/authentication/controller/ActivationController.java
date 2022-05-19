package com.samueldev.project.authentication.controller;

import com.samueldev.project.user.model.Activation;
import com.samueldev.project.user.service.ActivationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/activation")
public class ActivationController {

    private final ActivationService activationService;

    @GetMapping
    public ResponseEntity<List<Activation>> findAll() {

        return ResponseEntity.ok(activationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activation> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(activationService.findById(id));
    }
}
