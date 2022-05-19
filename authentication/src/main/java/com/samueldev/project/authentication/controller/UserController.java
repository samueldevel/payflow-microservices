package com.samueldev.project.authentication.controller;

import com.samueldev.project.user.model.User;
import com.samueldev.project.user.request.SignUpRequestBody;
import com.samueldev.project.user.response.SignUpResponseBody;
import com.samueldev.project.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listAllUsers() {

        return ResponseEntity.ok()
                .body(userService.findAllUsers());
    }

    @PostMapping
    public ResponseEntity<SignUpResponseBody> signUpUser(@Valid @RequestBody SignUpRequestBody signUpRequestBody) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.signUpUser(signUpRequestBody));
    }

    @GetMapping("/activate")
    public ResponseEntity<Void> activateUser(@RequestParam(name = "activation") UUID activation) {
        userService.unlockUser(activation);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(String.format("User with id %s was successfully deleted", id));
    }

}
