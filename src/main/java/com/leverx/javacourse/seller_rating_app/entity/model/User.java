package com.leverx.javacourse.seller_rating_app.entity.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private int password;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sold_by")
    private List<Item> sellersItems = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "author_id")
    private List<Comment> comments = new ArrayList<>();
    @Column(name = "created")
    private LocalDate created;
    @Column(name = "rating")
    private BigDecimal rating;
    @Column(name = "user_role")
    private UserRoles userRole;

    public User() {
    }

    public User(String login, int password, String firstName, String secondName, String email, LocalDate created, BigDecimal rating, UserRoles userRole) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.created = LocalDate.now();
        this.rating = new BigDecimal(0);
        this.userRole = userRole;
    }

    public int getId() {
        return Id;
    }

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

    public List<Item> getSellersItems() {
        return sellersItems;
    }

    public void setSellersItems(List<Item> sellersItems) {
        this.sellersItems = sellersItems;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
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

    @Override
    public String toString() {
        return "User{" + ", firstName=" + firstName + ", login='" + login + '\'' + ", secondName=" + secondName + ", created=" + created + ", rating=" + rating + ", userRole=" + userRole + '}';
    }
}
