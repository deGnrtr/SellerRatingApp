package com.leverx.javacourse.seller.rating.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "visitor")
public class Visitor extends User{

    @Column(name = "status")
    private String status = "NOT_VERIFIED";

    public Visitor() {
    }

    public Visitor(Long id, String login, String password, String firstName, String secondName, String email, LocalDate created, List<Comment> ownComments, UserRoles role, String status) {
        super(id, login, password, firstName, secondName, email, created, ownComments, role);
        this.status = status;
    }


    @Override
    public String toString() {
        return "Visitor" +
                "\n status='" + status + '\'' +
                "\n , id=" + id +
                "\n , login='" + login + '\'' +
                "\n , firstName='" + firstName + '\'' +
                "\n , secondName='" + secondName + '\'' +
                "\n , email='" + email + '\'' +
                "\n , created=" + created +
                "\n , ownComments=" + ownComments;
    }
}