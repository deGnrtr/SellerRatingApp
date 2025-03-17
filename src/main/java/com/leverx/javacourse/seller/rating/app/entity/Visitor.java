package com.leverx.javacourse.seller.rating.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "visitors")
public class Visitor extends User{

    public Visitor() {
    }

    public Visitor(Long id, String login, String password, String firstName, String secondName, String email, LocalDate created, List<Comment> ownComments) {
        super(id, login, password, firstName, secondName, email, created, ownComments, UserRoles.VISITOR);
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "id=" + id +
                ", comments=" + ownComments +
                ", id=" + id +
                ", login='" + login + '\'' +
                ", password=" + password +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                '}';
    }
}