package com.example.imageservice.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

/**
 * @author Neil Alishev
 */
@Component
public class JWTUtil {

    private static String secret = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public static DecodedJWT validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("meroni")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }
}
