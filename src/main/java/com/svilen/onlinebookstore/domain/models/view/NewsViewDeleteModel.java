package com.svilen.onlinebookstore.domain.models.view;

public class NewsViewDeleteModel {

    private String id;
    private String name;
    private String text;

    public NewsViewDeleteModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
