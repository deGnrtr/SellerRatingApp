package com.leverx.javacourse.seller_rating_app.entity.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sellers")
public class Seller extends User{

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "seller")
    private List<Item> sellersItems = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "seller")
    private List<Comment> assignedComments = new ArrayList<>();

    @Column(name = "rating")
    private BigDecimal rating;

    public Seller() {
        
    }
    
    public Seller(Long id, String login, int password, String firstName, String secondName, String email, LocalDate created,
                  List<Comment> assignedComments, List<Comment> ownComments,List<Item> sellersItems, BigDecimal rating) {
        super(id, login, password, firstName, secondName, email, created, ownComments, UserRoles.SELLER);
        this.Id = id;
        this.assignedComments = assignedComments;
        this.sellersItems = sellersItems;
        this.rating = rating;
    }

    public List<Comment> getAssignedComments() {
        return assignedComments;
    }

    public void setAssignedComments(List<Comment> assignedComments) {
        this.assignedComments = assignedComments;
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
        return "Seller{" +
                "Id=" + Id +
                ", sellersItems=" + sellersItems +
                ", assignedComments=" + assignedComments +
                ", ownComments=" + ownComments +
                ", rating=" + rating +
                ", login='" + login + '\'' +
                ", password=" + password +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                '}';
    }
}
