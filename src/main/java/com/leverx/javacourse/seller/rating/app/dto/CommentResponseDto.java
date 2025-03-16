package com.leverx.javacourse.seller.rating.app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CommentResponseDto {
    private Long Id;
    private String commentText;
    private LocalDate created;
    private String commentStatus;
    private BigDecimal ratingFromComment;
    private Long author;
    private Long seller;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public BigDecimal getRatingFromComment() {
        return ratingFromComment;
    }

    public void setRatingFromComment(BigDecimal rating) {
        this.ratingFromComment = rating;
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


