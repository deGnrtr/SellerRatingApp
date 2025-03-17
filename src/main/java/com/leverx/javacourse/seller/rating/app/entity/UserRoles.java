package com.leverx.javacourse.seller.rating.app.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoles implements GrantedAuthority {
    SELLER, VISITOR, ADMINISTRATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
