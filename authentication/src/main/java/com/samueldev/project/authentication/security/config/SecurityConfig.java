package com.samueldev.project.authentication.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samueldev.project.authentication.security.filter.JwtAuthenticationFilter;
import com.samueldev.project.authentication.security.jwt.JwtProperties;
import com.samueldev.project.authentication.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.samueldev.project.user.role.SecurityRole.ADMIN;
import static com.samueldev.project.user.role.SecurityRole.USER;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userService;
    private final JwtProperties jwtProperties;

    private final JwtUtil jwtUtil;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        setDefaultSecurityConfig(http);
        handlePathPermissions(http);

        http.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtUtil));
        http.httpBasic().authenticationEntryPoint(this::handleEntryPointException);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    private void setDefaultSecurityConfig(HttpSecurity http) throws Exception {
        http.csrf().disable();
        removeSessionPolicy(http);
    }

    private void handlePathPermissions(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(jwtProperties.getLoginUrl(), "/v1/user/activate/**").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/user/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/user/**").hasAnyRole(ADMIN.name(), USER.name());
    }

    private void handleEntryPointException(HttpServletRequest request,
                                           HttpServletResponse response, AuthenticationException authException) throws IOException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        new ObjectMapper().writeValue(response.getOutputStream(), authException.getMessage());
    }

    private void removeSessionPolicy(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
