package com.svilen.onlinebookstore.domain.models.service;

import org.hibernate.validator.constraints.Length;

public class NewsServiceModel extends BaseServiceModel{

    private String name;
    private String text;

    public NewsServiceModel() {

    }

    @Length(min = 4)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 10)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
