package com.svilen.onlinebookstore.web;

import com.svilen.onlinebookstore.domain.models.binding.NewsAddBindingModel;
import com.svilen.onlinebookstore.domain.models.service.NewsServiceModel;
import com.svilen.onlinebookstore.domain.models.view.DetailsNewsViewModel;
import com.svilen.onlinebookstore.domain.models.view.NewsViewAllModel;
import com.svilen.onlinebookstore.domain.models.view.NewsViewDeleteModel;
import com.svilen.onlinebookstore.domain.models.view.NewsViewEditModel;
import com.svilen.onlinebookstore.service.NewsService;
import com.svilen.onlinebookstore.validations.NewsAddValidator;
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
@RequestMapping("/news")
public class NewsController extends BaseController{
    private final NewsService newsService;
    private final ModelMapper modelMapper;
    private final NewsAddValidator newsAddValidator;

    @Autowired
    public NewsController(NewsService newsService, ModelMapper modelMapper, NewsAddValidator newsAddValidator) {
        this.newsService = newsService;
        this.modelMapper = modelMapper;
        this.newsAddValidator = newsAddValidator;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addNews() {
        return super.view("news/add-news");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addNewsConfirm(@ModelAttribute(name = "model") NewsAddBindingModel model, BindingResult bindingResult) throws Exception {

        newsAddValidator.validate(model, bindingResult);

        if (bindingResult.hasErrors()){
            return super.view("news/add-news");
        }

        this.newsService
                .addNews(this.modelMapper.map(model, NewsServiceModel.class));

        return super.redirect("/news/all");
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView allNews(ModelAndView modelAndView) {
        List<NewsViewAllModel> news = this.newsService
                .findAllNews()
                .stream()
                .map(n -> this.modelMapper.map(n, NewsViewAllModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("news", news);

        return super.view("news/all-news", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsNews(@PathVariable String id, ModelAndView modelAndView) {
        DetailsNewsViewModel detailsNewsViewModel = this.modelMapper
                .map(this.newsService.findNewsById(id), DetailsNewsViewModel.class);

        modelAndView.addObject("news", detailsNewsViewModel);

        return super.view("news/details-news", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editCategory(@PathVariable String id, ModelAndView modelAndView) {
        NewsViewEditModel newsViewEditModel = this.modelMapper
                .map(this.newsService.findNewsById(id), NewsViewEditModel.class);

        modelAndView.addObject("news", newsViewEditModel);

        return super.view("news/edit-news", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editNewsConfirm(@PathVariable String id, @ModelAttribute(name = "model") NewsAddBindingModel model) {

        this.newsService.editNews(id, this.modelMapper.map(model, NewsServiceModel.class));

        return super.redirect("/news/all");
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteNews(@PathVariable String id, ModelAndView modelAndView) {
        NewsViewDeleteModel newsViewDeleteModel = this.modelMapper
                .map(this.newsService.findNewsById(id), NewsViewDeleteModel.class);

        modelAndView.addObject("news", newsViewDeleteModel);

        return super.view("/news/delete-news", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteNewsConfirm(@PathVariable String id) {
        this.newsService
                .deleteNews(id);

        return super.redirect("/news/all");
    }
}
