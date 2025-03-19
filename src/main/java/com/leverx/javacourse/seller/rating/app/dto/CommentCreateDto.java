package com.leverx.javacourse.seller.rating.app.dto;

import java.math.BigDecimal;

public class CommentCreateDto {
    private String commentText;
    private String commentStatus;
    private BigDecimal ratingFromComment;

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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
