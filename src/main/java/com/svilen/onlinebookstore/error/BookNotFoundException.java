package com.svilen.onlinebookstore.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Book was not found!")
public class BookNotFoundException extends BaseException{

    public BookNotFoundException(String message) {
        super(HttpStatus.CONFLICT.value(), message);
    }
}
