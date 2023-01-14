package com.svilen.onlinebookstore.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Book name exist!")
public class BookNameAlreadyExistException extends BaseException{

    public BookNameAlreadyExistException(String message) {
        super(HttpStatus.CONFLICT.value(),message);

    }

}
