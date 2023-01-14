package com.svilen.onlinebookstore.web;

import com.svilen.onlinebookstore.error.BookNotFoundException;
import com.svilen.onlinebookstore.error.CategoryInvalidNameException;
import com.svilen.onlinebookstore.error.UsernameAlreadyExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandler extends BaseController{

    @org.springframework.web.bind.annotation.ExceptionHandler({BookNotFoundException.class})
    public ModelAndView handleNotFoundException(BookNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }


    @org.springframework.web.bind.annotation.ExceptionHandler({CategoryInvalidNameException.class})
    public ModelAndView handleCategoryInvalidNameException(CategoryInvalidNameException e) {
        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({UsernameAlreadyExistException.class})
    public ModelAndView handleUsernameException(CategoryInvalidNameException e) {
        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }

}
