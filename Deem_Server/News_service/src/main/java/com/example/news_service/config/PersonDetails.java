package com.example.news_service.config;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
@Data
public class PersonDetails implements UserDetails {
    private Long id;
    private String ROLE;
    private String username;
    private String password;

    /** Это доступ. Метка или адрес пользователя. Через эти 2 переменные
     * сервер ориентирует и ограничивает информацию конкретно в пределах этой метки.
     * Новости других факультетов или с других курсов не доступны.*/
    private String faculty;
    private Integer course;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(ROLE));
    }
}

