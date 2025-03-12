package com.leverx.javacourse.seller_rating_app.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "text")
    private String commentText;

    @Column(name = "created")
    private LocalDate created;

    @Column(name = "approved")
    private String commentStatus;

    @Column(name = "rating")
    private BigDecimal ratingFromComment;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    public void setId(Long id) {
        Id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Comment() {
    }

    public Comment(String commentText, LocalDate created) {
        this.commentText = commentText;
        this.created = LocalDate.now();
    }

    public Long getId() {
        return Id;
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

    @Override
    public String toString() {
        return "Comment{" + "commentText='" + commentText + '\'' + ", created=" + created + '}';
    }
}
