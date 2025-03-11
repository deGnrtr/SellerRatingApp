package com.leverx.javacourse.seller_rating_app.entity.dto;

import com.leverx.javacourse.seller_rating_app.entity.model.Comment;
import com.leverx.javacourse.seller_rating_app.entity.model.Item;
import com.leverx.javacourse.seller_rating_app.entity.model.UserRoles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserResponseDto {
    private final List<Item> sellersItems = new ArrayList<>();
    private final List<Comment> comments = new ArrayList<>();
    private int Id;
    private String login;
    private int password;
    private String firstName;
    private String secondName;
    private String email;
    private LocalDate created;
    private BigDecimal rating;
    private UserRoles userRole;
}
