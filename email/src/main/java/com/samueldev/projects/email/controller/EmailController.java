package com.samueldev.projects.email.controller;

import com.samueldev.projects.email.request.ActivationEmailRequestBody;
import com.samueldev.projects.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/email")
public class EmailController {
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> sendActivationEmail(@Valid @RequestBody ActivationEmailRequestBody activationEmailRequestBody) {
        emailService.sendActivationEmail(activationEmailRequestBody);
        
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
