package com.svilen.onlinebookstore.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "news")
public class News extends BaseEntity{
    private String name;
    private String text;

    public News() {

    }

    @Column(name = "name", nullable = false, unique = true, updatable = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "text", nullable = false, updatable = true)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
