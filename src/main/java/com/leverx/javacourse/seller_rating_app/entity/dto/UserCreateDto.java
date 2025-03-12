package com.leverx.javacourse.seller_rating_app.entity.dto;

import com.leverx.javacourse.seller_rating_app.entity.model.Comment;
import com.leverx.javacourse.seller_rating_app.entity.model.UserRoles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class UserCreateDto {
    private String login;
    private int password;
    private String firstName;
    private String secondName;
    private String email;
    private List<CommentCreateDto> comments;
    private LocalDate created;
    private BigDecimal rating;
    private UserRoles userRole;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CommentCreateDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentCreateDto> comments) {
        this.comments = comments;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public UserRoles getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoles userRole) {
        this.userRole = userRole;
    }
}
