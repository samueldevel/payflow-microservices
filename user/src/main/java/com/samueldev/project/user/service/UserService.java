package com.samueldev.project.user.service;

import com.samueldev.project.user.mapper.SignUpMapper;
import com.samueldev.project.user.model.User;
import com.samueldev.project.user.repository.UserRepository;
import com.samueldev.project.user.request.SignUpRequestBody;
import com.samueldev.project.user.response.SignUpResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;

    private final ActivationService activationService;

    public List<User> findAllUsers() {

        return userRepository.findAll();
    }

    public User findById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("User with id %s does not found!", id)));
    }

    public User saveUser(User user) {

        return userRepository.save(user);
    }

    @Transactional
    public SignUpResponseBody signUpUser(SignUpRequestBody signUpRequestBody) {
        User user = SignUpMapper.toUser(signUpRequestBody);
        user.setupToSignUp(encoder());

        User userSaved = userRepository.save(user);
        activationService.sendActivationEmail(userSaved);

        return SignUpResponseBody.buildWithUser(userSaved);
    }

    public void unlockUser(UUID id) {
        User user = activationService.findById(id).getUser();
        user.setupAfterConfirmed();
        log.info("User got for activation link = {}", user);

        this.saveUser(user);
    }

    public void deleteUser(Long id) {
        User userFound = findById(id);
        activationService.deleteUserWithActivationId(userFound);
        userRepository.delete(userFound);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
