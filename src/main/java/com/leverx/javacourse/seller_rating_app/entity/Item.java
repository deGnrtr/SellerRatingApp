package com.leverx.javacourse.seller_rating_app.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false, unique = true)
    private int itemId;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "game_title")
    private String gameTitle;

    @Column(name = "created", nullable = false, unique = true)
    private Date created;

    @Column(name = "updated", nullable = false, unique = true)
    private Date updated;

    public Item() {
    }

    public Item(String title) {
        this.title = title;
        this.created = new Date();
    }

    public int getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item item)) return false;
        return Objects.equals(title, item.title) && Objects.equals(created, item.created) && Objects.equals(updated, item.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, created, updated);
    }

    @Override
    public String toString() {
        return "Item{" + "title='" + title + '\'' + ", description='" + description + '\'' + ", created=" + created + ", updated=" + updated + '}';
    }
}
