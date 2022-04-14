package ru.kuranov.pull.entity.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    INTERVIEWED;

    @Override
    public String getAuthority() {
        return name();
    }
}
