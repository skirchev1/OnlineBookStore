package com.svilen.onlinebookstore.validations.impl;

import com.svilen.onlinebookstore.domain.entities.Book;
import com.svilen.onlinebookstore.domain.entities.Category;
import com.svilen.onlinebookstore.validations.BookValidationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookValidationServiceImpl implements BookValidationService {
    @Override
    public boolean isValid(Book book) {
        return book!= null && areCategoriesAreValid(book.getCategories());
    }

    private boolean areCategoriesAreValid(List<Category> categories) {
        return categories != null && !categories.isEmpty();
    }
}
