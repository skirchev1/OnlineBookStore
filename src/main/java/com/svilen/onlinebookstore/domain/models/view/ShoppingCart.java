package com.svilen.onlinebookstore.domain.models.view;

import java.io.Serializable;

public class ShoppingCart implements Serializable {

    private BookViewModel bookViewModel;
    private int quantity;

    public ShoppingCart() {

    }

    public BookViewModel getBookViewModel() {
        return bookViewModel;
    }

    public void setBookViewModel(BookViewModel bookViewModel) {
        this.bookViewModel = bookViewModel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
