package com.leverx.javacourse.seller.rating.app.dto;

import java.math.BigDecimal;

public class ReviewCreateDto {
    private String reviewText;
    private BigDecimal ratingFromReview;

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public BigDecimal getRatingFromReview() {
        return ratingFromReview;
    }

    public void setRatingFromReview(BigDecimal rating) {
        this.ratingFromReview = rating;
    }
}
