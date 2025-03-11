package com.leverx.javacourse.seller_rating_app.entity.dto;

import com.leverx.javacourse.seller_rating_app.entity.model.UserRoles;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UserCreateDto {
    private String login;
    private int password;
    private String firstName;
    private String secondName;
    private String email;
    private String comment;
    private LocalDate created;
    private BigDecimal rating;
    private UserRoles userRole;
}
