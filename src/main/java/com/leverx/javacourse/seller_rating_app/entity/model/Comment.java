package com.leverx.javacourse.seller_rating_app.entity.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "text")
    private String commentText;

    @Column(name = "created")
    private LocalDate created;

    @Column(name = "approved")
    private String commentStatus;

    public Comment() {
    }

    public Comment(String commentText, LocalDate created) {
        this.commentText = commentText;
        this.created = LocalDate.now();
    }

    public int getId() {
        return Id;
    }

    public String getCommentText() {
        return commentText;
    }

    public LocalDate getCreated() {
        return created;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    @Override
    public String toString() {
        return "Comment{" + "commentText='" + commentText + '\'' + ", created=" + created + '}';
    }
}
