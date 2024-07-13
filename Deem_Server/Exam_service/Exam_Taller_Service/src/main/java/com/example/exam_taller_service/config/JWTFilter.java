package com.example.exam_taller_service.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.exam_taller_service.services.MainService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private MainService service;

    private PersonDetails details;

    public static boolean isDisable;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {
        String authHeader = httpServletRequest.getHeader("Authorization");

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                DecodedJWT jwtDec = jwtUtil.validateTokenAndRetrieveClaim(jwt);

                if (isDisable)
                    if (jwtDec.getClaim("ROLE").asString().equals("STUDENT"))
                        throw new IOException("Страница отключена");

                details = new PersonDetails();
                details.setUsername(jwtDec.getClaim("username").asString());
                details.setPassword(jwtDec.getClaim("password").asString());
                details.setId(jwtDec.getClaim("id").asLong());
                details.setROLE(jwtDec.getClaim("ROLE").asString());
                details.setCourse(jwtDec.getClaim("course").asInt());
                details.setFaculty(jwtDec.getClaim("faculty").asString());

                UserDetails userDetails = details;

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                userDetails.getPassword(),
                                userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            //Ограничение по участникам
            if (!(details.getROLE().equals("ROLE_HIGH") || details.getROLE().equals("ROLE_ADMIN") ||
                    details.getROLE().equals("ROLE_PRESIDENT_COUNCIL"))) { //администраторов пропускаем к доступу
                List<Long> members = service.getMembers();


                if (!members.contains(details.getId()))
                    throw new IOException("Вы не являетесь участником экзамена");
            }

            //
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

}
