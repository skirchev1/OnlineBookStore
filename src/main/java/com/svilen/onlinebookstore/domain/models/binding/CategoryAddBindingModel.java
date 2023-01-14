package com.svilen.onlinebookstore.domain.models.binding;

import org.hibernate.validator.constraints.Length;

public class CategoryAddBindingModel {

    private String name;

    public CategoryAddBindingModel() {

    }

    @Length(min = 4)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
