package com.leverx.javacourse.seller_rating_app.entity.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "users")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long Id;

    @Column(name = "login")
    protected String login;

    @Column(name = "password")
    protected String password;

    @Column(name = "first_name")
    protected String firstName;

    @Column(name = "second_name")
    protected String secondName;

    @Column(name = "email")
    protected String email;

    @Column(name = "created")
    protected LocalDate created;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    protected UserRoles role;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "author")
    protected List<Comment> ownComments = new ArrayList<>();

    public User() {
    }

    public User(Long id, String login, String password, String firstName, String secondName, String email, LocalDate created, List<Comment> ownComments, UserRoles role) {
        Id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.created = created;
        this.ownComments = ownComments;
        this.role = role;
    }

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

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public List<Comment> getOwnComments() {
        return ownComments;
    }

    public void setOwnComments(List<Comment> ownComments) {
        this.ownComments = ownComments;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = UserRoles.valueOf(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", login='" + login + '\'' +
                ", password=" + password +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                ", ownComments=" + ownComments +
                '}';
    }
}
