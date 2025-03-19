package com.leverx.javacourse.seller.rating.app.dto;

import java.util.List;

public class UserCreateDto {
    private String login;
    private String password;
    private String firstName;
    private String secondName;
    private String email;
    private List<CommentCreateDto> assignedComments;
    private String role;

    public String getLogin() {
        return login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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

    public List<CommentCreateDto> getAssignedComments() {
        return assignedComments;
    }

    public void setAssignedComments(List<CommentCreateDto> assignedComments) {
        this.assignedComments = assignedComments;
    }

}
