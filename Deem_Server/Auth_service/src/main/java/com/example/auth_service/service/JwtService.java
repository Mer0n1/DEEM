package com.example.auth_service.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.auth_service.models.Account;
import com.example.auth_service.models.LocationStudent;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtService {

    public static String secret;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        secret = environment.getProperty("SECRET");
    }

    public String generateToken(Account account, LocationStudent locationStudent) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("username", account.getUsername())
                .withClaim("id", account.getId())
                .withClaim("ROLE", account.getROLE())
                .withClaim("course", locationStudent.getCourse())
                .withClaim("faculty", locationStudent.getFaculty())
                .withIssuedAt(new Date())
                .withIssuer("meroni")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("meroni")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }

    public static DecodedJWT validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("meroni")
                .build();

        return verifier.verify(token);
    }

}