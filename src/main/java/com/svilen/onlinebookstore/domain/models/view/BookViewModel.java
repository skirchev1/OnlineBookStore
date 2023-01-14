package com.svilen.onlinebookstore.domain.models.view;

import com.svilen.onlinebookstore.domain.entities.Category;

import java.math.BigDecimal;
import java.util.List;

public class BookViewModel {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private List<Category> categories;

    public BookViewModel() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
