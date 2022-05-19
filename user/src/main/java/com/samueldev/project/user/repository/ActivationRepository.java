package com.samueldev.project.user.repository;

import com.samueldev.project.user.model.Activation;
import com.samueldev.project.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ActivationRepository extends JpaRepository<Activation, UUID> {

    Optional<Activation> findByUser(User user);
}
