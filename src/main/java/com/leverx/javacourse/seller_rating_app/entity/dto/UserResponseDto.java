package com.leverx.javacourse.seller_rating_app.entity.dto;

import com.leverx.javacourse.seller_rating_app.entity.model.UserRoles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserResponseDto {
    private Long Id;
    private String login;
    private String firstName;
    private String secondName;
    private String email;
    private List<ItemResponseDto> sellersItems = new ArrayList<>();
    private List<CommentResponseDto> comments = new ArrayList<>();
    private LocalDate created;
    private BigDecimal rating;
    private UserRoles role;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public List<ItemResponseDto> getSellersItems() {
        return sellersItems;
    }

    public void setSellersItems(List<ItemResponseDto> sellersItems) {
        this.sellersItems = sellersItems;
    }

    public List<CommentResponseDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponseDto> comments) {
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

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }
}
