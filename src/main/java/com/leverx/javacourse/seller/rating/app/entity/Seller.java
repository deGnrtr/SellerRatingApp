package com.leverx.javacourse.seller.rating.app.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seller")
public class Seller extends User{

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "seller")
    private List<Item> sellersItems = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "seller")
    private List<Review> assignedReviews = new ArrayList<>();

    @Column(name = "rating")
    private BigDecimal rating = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);

    public Seller() {

    }

    public Seller(Long id, String login, String password, String firstName, String secondName, String email
            , LocalDate created, UserRoles role, List<Review> ownReviews, String status, List<Item> sellersItems
            , List<Review> assignedReviews, BigDecimal rating) {
        super(id, login, password, firstName, secondName, email, created, role, ownReviews, status);
        this.sellersItems = sellersItems;
        this.assignedReviews = assignedReviews;
        this.rating = rating;
    }

    public List<Review> getAssignedReviews() {
        return assignedReviews;
    }

    public void setAssignedReviews(List<Review> assignedReviews) {
        this.assignedReviews = assignedReviews;
    }

    public List<Item> getSellersItems() {
        return sellersItems;
    }

    public void setSellersItems(List<Item> sellersItems) {
        this.sellersItems = sellersItems;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }


    @Override
    public String toString() {
        return "Seller" +
                "\n sellersItems=" + sellersItems +
                "\n , assignedReviews=" + assignedReviews +
                "\n , rating=" + rating +
                "\n , id=" + id +
                "\n , login='" + login + '\'' +
                ",\n  firstName='" + firstName + '\'' +
                "\n , secondName='" + secondName + '\'' +
                "\n , email='" + email + '\'' +
                "\n , created=" + created +
                ", ownReviews=" + ownReviews;
    }
}
