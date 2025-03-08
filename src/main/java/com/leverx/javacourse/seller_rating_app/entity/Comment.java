package com.leverx.javacourse.seller_rating_app.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "text")
    private String commentText;

    @Column(name = "created")
    private Date created;

    @Column(name = "approved")
    private boolean commentApproved;

    public Comment() {
    }

    public Comment(String commentText, Date created) {
        this.commentText = commentText;
        this.created = new Date();
    }

    public int getCommentId() {
        return commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public Date getCreated() {
        return created;
    }

    public boolean isCommentApproved() {
        return commentApproved;
    }

    public void setCommentApproved(boolean commentApproved) {
        this.commentApproved = commentApproved;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Comment comment)) return false;
        return Objects.equals(commentText, comment.commentText) && Objects.equals(created, comment.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentText, created);
    }

    @Override
    public String toString() {
        return "Comment{" + "commentText='" + commentText + '\'' + ", created=" + created + '}';
    }
}
