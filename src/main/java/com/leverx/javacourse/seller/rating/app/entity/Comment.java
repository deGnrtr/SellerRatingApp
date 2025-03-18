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
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String commentText;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDate created;

    @Column(name = "status")
    private String status = "NOT_VERIFIED";

    @Column(name = "rating")
    private BigDecimal ratingFromComment;

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

    public Comment() {
    }
//TODO date
    public Comment(String commentText, LocalDate created) {
        this.commentText = commentText;
        this.created = LocalDate.now();
    }

    public Long getId() {
        return id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String commentStatus) {
        this.status = status;
    }

    public BigDecimal getRatingFromComment() {
        return ratingFromComment;
    }

    public void setRatingFromComment(BigDecimal rating) {
        this.ratingFromComment = rating;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }


    @Override
    public String toString() {
        return "Comment" +
                "\n id=" + id +
                "\n , commentText='" + commentText + '\'' +
                "\n , created=" + created +
                "\n , status='" + status + '\'' +
                "\n , ratingFromComment=" + ratingFromComment +
                "\n , author=" + author +
                "\n , seller=" + seller;
    }
}
