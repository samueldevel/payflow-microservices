package com.samueldev.project.authentication.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samueldev.project.authentication.security.jwt.JwtUtil;
import com.samueldev.project.user.model.User;
import com.samueldev.project.user.request.SignInRequestBody;
import com.samueldev.project.user.response.BadRequestResponseBody;
import com.samueldev.project.user.response.SignInResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Collections.emptyList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("Attempting authentication ...");
        SignInRequestBody userByRequest = getUserByRequest(request);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userByRequest.getUsername(), userByRequest.getPassword(), emptyList());

        usernamePasswordAuthenticationToken.setDetails(userByRequest);

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        log.info("Authentication was successful for the user '{}'", authResult.getName());
        User user = (User) authResult.getPrincipal();
        log.info("User got by principal is {}", user);

        String token = jwtUtil.signToken(user);
        log.info("Token generated - successfulAuthentication");

        SignInResponseBody signInResponseBody = new SignInResponseBody(user.getRole().name(), token);

        new ObjectMapper().writeValue(response.getOutputStream(), signInResponseBody);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.info("user was unsuccessfully authenticated");

        BadRequestResponseBody badRequestBody = BadRequestResponseBody.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(failed.getMessage())
                .developerMessage("An error occurred as attempting to authenticate")
                .build();

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);


        new ObjectMapper().writeValue(response.getOutputStream(), badRequestBody);
    }

    private SignInRequestBody getUserByRequest(HttpServletRequest request) throws IOException {
        SignInRequestBody signInRequestBody = new ObjectMapper().readValue(request.getInputStream(), SignInRequestBody.class);
        log.info("Getting user by request, {}", signInRequestBody);

        if (signInRequestBody == null)
            throw new UsernameNotFoundException("Unable to retrieve the username or password");

        return signInRequestBody;
    }
}
