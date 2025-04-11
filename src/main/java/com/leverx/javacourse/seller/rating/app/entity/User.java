package com.leverx.javacourse.seller.rating.app.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

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
    protected Long id;

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

    @CreatedDate
    @Column(name = "created_date")
    protected LocalDate created;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    protected UserRoles role;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "author")
    protected List<Review> ownReviews = new ArrayList<>();

    @Column(name = "status")
    protected String status = "NOT_VERIFIED";

    public User() {
    }

    public User(Long id, String login, String password, String firstName, String secondName, String email
            , LocalDate created, UserRoles role, List<Review> ownReviews, String status) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.created = created;
        this.role = role;
        this.ownReviews = ownReviews;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Review> getOwnComments() {
        return ownReviews;
    }

    public void setOwnComments(List<Review> ownReviews) {
        this.ownReviews = ownReviews;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = UserRoles.valueOf(role);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
