package com.leverx.javacourse.seller.rating.app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReviewResponseDto {
    private Long Id;
    private String reviewText;
    private LocalDate created;
    private String status;
    private BigDecimal ratingFromReview;
    private Long author;
    private Long seller;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getRatingFromReview() {
        return ratingFromReview;
    }

    public void setRatingFromReview(BigDecimal rating) {
        this.ratingFromReview = rating;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public Long getSeller() {
        return seller;
    }

    public void setSeller(Long seller) {
        this.seller = seller;
    }
}


