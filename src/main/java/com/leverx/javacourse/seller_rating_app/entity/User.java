package com.leverx.javacourse.seller_rating_app.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private int userId;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false, unique = true)
    private int password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sold_by")
    private List<Item> sellersItems;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "author_id")
    private List<Comment> comments;

    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "rating", nullable = false)
    private BigDecimal rating;

    @Column(name = "user_role", nullable = false)
    private UserRoles userRole;

    public User() {
    }

    public User(String login, int password, String firstName, String secondName, String email, Date created, BigDecimal rating, UserRoles userRole) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.created = new Date();
        this.rating = new BigDecimal(0);
        this.userRole = userRole;
    }

    public int getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
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

    public Date getCreated() {
        return created;
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
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(login, user.login) && Objects.equals(firstName, user.firstName) && Objects.equals(secondName, user.secondName) && Objects.equals(created, user.created) && userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, firstName, secondName, created, userRole);
    }

    @Override
    public String toString() {
        return "User{" + ", firstName=" + firstName + ", login='" + login + '\'' + ", secondName=" + secondName + ", created=" + created + ", rating=" + rating + ", userRole=" + userRole + '}';
    }
}
