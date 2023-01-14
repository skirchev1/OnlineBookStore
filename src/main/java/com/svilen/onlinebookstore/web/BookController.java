package com.svilen.onlinebookstore.web;

import com.svilen.onlinebookstore.domain.models.binding.BookAddBindingModel;
import com.svilen.onlinebookstore.domain.models.service.BookServiceModel;
import com.svilen.onlinebookstore.domain.models.view.AllBookViewModel;
import com.svilen.onlinebookstore.domain.models.view.BookViewModel;
import com.svilen.onlinebookstore.domain.models.view.DetailsBookViewModel;
import com.svilen.onlinebookstore.service.BookService;
import com.svilen.onlinebookstore.service.CategoryService;
import com.svilen.onlinebookstore.validations.BookAddValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BookController extends BaseController {
    private final BookService bookService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final BookAddValidator bookAddValidator;

    @Autowired
    public BookController(BookService bookService, CategoryService categoryService, ModelMapper modelMapper, BookAddValidator bookAddValidator) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.bookAddValidator = bookAddValidator;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addBook(){
        return super.view("book/add-book");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addBookConfirm(@ModelAttribute(name = "model") BookAddBindingModel model, BindingResult bindingResult) throws Exception {

        bookAddValidator.validate(model, bindingResult);

        if (bindingResult.hasErrors()){
            return super.view("book/add-book");
        }

        BookServiceModel bookServiceModel = this.modelMapper.map(model, BookServiceModel.class);

        bookServiceModel.setCategories(
                this.categoryService
                        .findAllCategories()
                        .stream()
                        .filter(categories -> model.getCategories().contains(categories.getId()))
                        .collect(Collectors.toList()));

        this.bookService.addBook(bookServiceModel);

        return super.redirect("/books/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allProducts(@ModelAttribute(name = "model") BookAddBindingModel model,
                                    ModelAndView modelAndView) {

        List<AllBookViewModel> books = this.bookService
                .findAllBooks()
                .stream()
                .map(b -> this.modelMapper.map(b, AllBookViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("books", books);

        return super.view("book/all-books", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsBook(@PathVariable String id, ModelAndView modelAndView) {
        DetailsBookViewModel detailsBookViewModel = this.modelMapper
                .map(this.bookService.findBookById(id), DetailsBookViewModel.class);

        modelAndView.addObject("book", detailsBookViewModel);

        return super.view("book/details-book", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editBook(@PathVariable String id, ModelAndView modelAndView) {

        BookViewModel bookViewModel = this.modelMapper
                .map(this.bookService.findBookById(id), BookViewModel.class);

        modelAndView.addObject("book", bookViewModel);

        return super.view("/book/edit-book", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editBookConfirm(@PathVariable String id, @ModelAttribute BookAddBindingModel model) {
        BookServiceModel bookServiceModel = this.modelMapper.map(model, BookServiceModel.class);
        bookServiceModel.setCategories(this.categoryService
                .findAllCategories()
                .stream()
                .filter(categories -> model.getCategories().contains(categories.getId()))
                .collect(Collectors.toList())
        );

        this.bookService
                .editBook(id, bookServiceModel);

        return super.redirect("/books/details/" + id);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteBook(@PathVariable String id, ModelAndView modelAndView) {
        BookViewModel bookViewModel = this.modelMapper
                .map(this.bookService.findBookById(id), BookViewModel.class);

        modelAndView.addObject("book", bookViewModel);

        return super.view("book/delete-book", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteProductConfirm(@PathVariable String id) {
        this.bookService
                .deleteBook(id);

        return super.redirect("/books/all");
    }
}
