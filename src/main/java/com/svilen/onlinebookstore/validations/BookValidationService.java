package com.svilen.onlinebookstore.validations;


import com.svilen.onlinebookstore.domain.entities.Book;

public interface BookValidationService {
    boolean isValid(Book book);
}
