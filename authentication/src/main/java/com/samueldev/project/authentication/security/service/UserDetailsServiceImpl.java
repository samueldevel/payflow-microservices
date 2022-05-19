package com.samueldev.project.authentication.security.service;

import com.samueldev.project.authentication.security.user.CustomUserDetails;
import com.samueldev.project.user.model.User;
import com.samueldev.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final String ERR_MESSAGE = String.format("User with username %s not found!", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ERR_MESSAGE));

        log.info("User was successfully found in - loadUserByUsername");

        return new CustomUserDetails(user);
    }
}
