package com.leverx.javacourse.seller.rating.app.dto;

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
    private List<CommentResponseDto> ownComments = new ArrayList<>();
    private List<CommentResponseDto> assignedComments = new ArrayList<>();
    private LocalDate created;
    private BigDecimal rating;
    private String status;
    private String role;

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

    public List<CommentResponseDto> getOwnComments() {
        return ownComments;
    }

    public void setOwnComments(List<CommentResponseDto> comments) {
        this.ownComments = comments;
    }

    public List<CommentResponseDto> getAssignedComments() {
        return assignedComments;
    }

    public void setAssignedComments(List<CommentResponseDto> assignedComments) {
        this.assignedComments = assignedComments;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
