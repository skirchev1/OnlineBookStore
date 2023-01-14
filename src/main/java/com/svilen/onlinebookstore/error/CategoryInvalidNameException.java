package com.svilen.onlinebookstore.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid category name!")
public class CategoryInvalidNameException extends BaseException{

    public CategoryInvalidNameException(String message) {
        super(HttpStatus.CONFLICT.value(),message);

    }

}
