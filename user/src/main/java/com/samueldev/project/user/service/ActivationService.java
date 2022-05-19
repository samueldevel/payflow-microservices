package com.samueldev.project.user.service;

import com.samueldev.project.user.client.EmailClient;
import com.samueldev.project.user.mapper.UserMapper;
import com.samueldev.project.user.model.Activation;
import com.samueldev.project.user.model.User;
import com.samueldev.project.user.repository.ActivationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Log4j2
public class ActivationService {
    private final ActivationRepository activationRepository;

    public List<Activation> findAll() {

        return activationRepository.findAll();
    }

    public Activation findById(UUID id) {

//        return findAll()
//                .stream().filter(activation -> activation.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Id not found"));

        return activationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Activation with id %s not found", id)));
    }

    public Activation saveActivation(Activation activation) {

        return activationRepository.save(activation);
    }

    public void sendActivationEmail(User user) {
        Activation activationSaved = saveActivation(new Activation(user));
        log.info("ActivationSaved - {}", activationSaved);

        EmailClient.sendActivationEmail(UserMapper.toActivationEmail(user, activationSaved));
        log.info("Email was successfully sent!");
    }

    public void deleteUserWithActivationId(User user) {
        Activation activationFound = activationRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User used to delete activation does not found!"));

        activationRepository.delete(activationFound);
    }

}