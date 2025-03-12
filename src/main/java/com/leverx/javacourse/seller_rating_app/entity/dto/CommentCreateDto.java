package com.leverx.javacourse.seller_rating_app.entity.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CommentCreateDto {
    private String commentText;
    private LocalDate created;
    private String commentStatus;
    private BigDecimal ratingFromComment;

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
}
