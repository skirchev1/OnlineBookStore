package com.svilen.onlinebookstore.domain.models.service;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

public class BookServiceModel extends BaseServiceModel{

    private String name;
    private String description;
    private BigDecimal price;
    private List<CategoryServiceModel> categories;

    public BookServiceModel() {

    }

    @Length(min = 6)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 10)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DecimalMin(value = "1")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<CategoryServiceModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryServiceModel> categories) {
        this.categories = categories;
    }
}
