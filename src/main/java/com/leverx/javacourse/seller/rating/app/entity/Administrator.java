package com.leverx.javacourse.seller.rating.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "administrator")
public class Administrator extends User{

    public Administrator() {
    }

    public Administrator(Long id, String login, String password, String firstName, String secondName, String email, LocalDate created, List<Comment> ownComments) {
        super(id, login, password, firstName, secondName, email, created, ownComments, UserRoles.ADMINISTRATOR);
    }

    @Override
    public String toString() {
        return "Administrator{" +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}