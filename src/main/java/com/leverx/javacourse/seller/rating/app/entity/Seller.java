package com.leverx.javacourse.seller.rating.app.entity;

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
@Table(name = "seller")
public class Seller extends User{

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "seller")
    private List<Item> sellersItems = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "seller")
    private List<Comment> assignedComments = new ArrayList<>();

    @Column(name = "rating")
    private BigDecimal rating = new BigDecimal(0);

    @Column(name = "status")
    private String status = "NOT_VERIFIED";

    public Seller() {

    }
    
    public Seller(Long id, String login, String password, String firstName, String secondName, String email, LocalDate created,
                  List<Comment> assignedComments, List<Comment> ownComments, List<Item> sellersItems, BigDecimal rating, String status) {
        super(id, login, password, firstName, secondName, email, created, ownComments, UserRoles.SELLER);
        this.id = id;
        this.assignedComments = assignedComments;
        this.sellersItems = sellersItems;
        this.rating = rating;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Seller" +
                "\n sellersItems=" + sellersItems +
                "\n , assignedComments=" + assignedComments +
                "\n , rating=" + rating +
                "\n , status='" + status + '\'' +
                "\n , id=" + id +
                "\n , login='" + login + '\'' +
                ",\n  firstName='" + firstName + '\'' +
                "\n , secondName='" + secondName + '\'' +
                "\n , email='" + email + '\'' +
                "\n , created=" + created +
                ", ownComments=" + ownComments;
    }
}
