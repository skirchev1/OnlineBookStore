package com.svilen.onlinebookstore.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

public class BookAddBindingModel {

    private String name;
    private String description;
    private BigDecimal price;
    private List<String> categories;

    public BookAddBindingModel() {

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

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
