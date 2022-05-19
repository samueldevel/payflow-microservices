package com.samueldev.project.authentication.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.samueldev.project.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;

@Log4j2
@Service
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProperties properties;
    private static final String ISSUER = "payflow";

    public String signToken(User user) {
        Algorithm algorithm = getHMAC256Algorithm();

        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + (properties.getExpiration() * 1000L)))
                .withClaim("authorities", user.getRole().name())
                .sign(algorithm);
    }

    public void verifyToken(String token, User user) {
        Algorithm algorithm = getHMAC256Algorithm();

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .withSubject(user.getUsername())
                .withClaim("authorities", user.getRole().name())
                .build();

        verifier.verify(token);
    }

    private Algorithm getHMAC256Algorithm() {

        return Algorithm.HMAC256(properties.getSecret());
    }
}
