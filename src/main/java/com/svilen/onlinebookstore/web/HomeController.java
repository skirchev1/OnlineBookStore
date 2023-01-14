package com.svilen.onlinebookstore.web;

import com.svilen.onlinebookstore.domain.models.service.BookServiceModel;
import com.svilen.onlinebookstore.domain.models.view.BookViewModel;
import com.svilen.onlinebookstore.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private final BookService bookService;
    private final ModelMapper modelMapper;

    public HomeController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    public ModelAndView index() {
        return super.view("index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView) {
        List<BookServiceModel> bookServiceModels = this.bookService.findAllBooks();
        List<BookViewModel> viewModels = bookServiceModels.stream().map(p -> this.modelMapper.map(p, BookViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("books", viewModels);
        return super.view("home", modelAndView);
    }

}
