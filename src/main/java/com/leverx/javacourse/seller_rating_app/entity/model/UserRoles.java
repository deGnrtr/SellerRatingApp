package com.leverx.javacourse.seller_rating_app.entity.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoles implements GrantedAuthority {
    SELLER, VISITOR, ADMINISTRATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
