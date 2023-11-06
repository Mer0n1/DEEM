package com.example.push_notification_service.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JWTFilter extends OncePerRequestFilter {

    public static PersonDetails createPersonDetails(String jwt) {

        DecodedJWT jwtDec = JWTUtil.validateTokenAndRetrieveClaim(jwt);

        PersonDetails details = new PersonDetails();
        details.setUsername(jwtDec.getClaim("username").asString());
        details.setPassword(jwtDec.getClaim("password").asString());
        details.setId(jwtDec.getClaim("id").asLong());

        return details;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {
        String authHeader = httpServletRequest.getHeader("Authorization");

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                DecodedJWT jwtDec = JWTUtil.validateTokenAndRetrieveClaim(jwt);

                PersonDetails details = new PersonDetails();
                details.setUsername(jwtDec.getClaim("username").asString());
                details.setPassword(jwtDec.getClaim("password").asString());

                UserDetails userDetails = details;

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                userDetails.getPassword(),
                                userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
