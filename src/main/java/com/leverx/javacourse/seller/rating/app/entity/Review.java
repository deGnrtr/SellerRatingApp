package com.leverx.javacourse.seller.rating.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String reviewText;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDate created;

    @Column(name = "status")
    private String status = "NOT_VERIFIED";

    @Column(name = "rating")
    private BigDecimal ratingFromReview;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @ManyToOne
    @JoinColumn(name = "seller")
    private User seller;

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Review() {
    }

    public Review(Long id, String reviewText, LocalDate created, String status, BigDecimal ratingFromReview, User author, User seller) {
        this.id = id;
        this.reviewText = reviewText;
        this.created = created;
        this.status = status;
        this.ratingFromReview = ratingFromReview;
        this.author = author;
        this.seller = seller;
    }

    public Long getId() {
        return id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String reviewStatus) {
        this.status = reviewStatus;
    }

    public BigDecimal getRatingFromReview() {
        return ratingFromReview;
    }

    public void setRatingFromReview(BigDecimal rating) {
        this.ratingFromReview = rating;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }


    @Override
    public String toString() {
        return "Review" +
                "\n id=" + id +
                "\n , reviewText='" + reviewText + '\'' +
                "\n , created=" + created +
                "\n , status='" + status + '\'' +
                "\n , ratingFromReview=" + ratingFromReview +
                "\n , author=" + author +
                "\n , seller=" + seller;
    }
}
