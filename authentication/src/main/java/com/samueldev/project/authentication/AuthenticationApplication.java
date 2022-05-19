package com.samueldev.project.authentication;

import com.samueldev.project.authentication.security.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan({"com.samueldev.project.user.service, com.samueldev.project.authentication"})
@EntityScan({"com.samueldev.project.user.model", "com.samueldev.project.authentication.model"})
@EnableJpaRepositories({"com.samueldev.project.user.repository", "com.samueldev.project.authentication.repository"})
@EnableConfigurationProperties(JwtProperties.class)
public class AuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }

}
