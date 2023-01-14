package com.svilen.onlinebookstore.web;

import com.svilen.onlinebookstore.domain.models.binding.CategoryAddBindingModel;
import com.svilen.onlinebookstore.domain.models.service.CategoryServiceModel;
import com.svilen.onlinebookstore.domain.models.view.CategoryViewAllModel;
import com.svilen.onlinebookstore.domain.models.view.CategoryViewDeleteModel;
import com.svilen.onlinebookstore.domain.models.view.CategoryViewEditModel;
import com.svilen.onlinebookstore.domain.models.view.CategoryViewModel;
import com.svilen.onlinebookstore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addCategory() {
        return super.view("category/add-category");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addCategoryConfirm(@ModelAttribute(name = "model") CategoryAddBindingModel model) {
        this.categoryService
                .addCategory(this.modelMapper.map(model, CategoryServiceModel.class));

        return super.redirect("/categories/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allCategory(ModelAndView modelAndView) {
        List<CategoryViewAllModel> categories = this.categoryService
                .findAllCategories()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryViewAllModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("categories", categories);

        return super.view("category/all-categories", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editCategory(@PathVariable String id, ModelAndView modelAndView) {
        CategoryViewEditModel categoryViewEditModel = this.modelMapper
                .map(this.categoryService.findById(id), CategoryViewEditModel.class);

        modelAndView.addObject("category", categoryViewEditModel);

        return super.view("category/edit-category", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editCategoryConfirm(@PathVariable String id, @ModelAttribute(name = "model")
            CategoryAddBindingModel model) {

        this.categoryService.editCategory(id, this.modelMapper.map(model, CategoryServiceModel.class));

        return super.redirect("/categories/all");
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteCategory(@PathVariable String id, ModelAndView modelAndView) {
        CategoryViewDeleteModel categoryViewDeleteModel = this.modelMapper
                .map(this.categoryService.findById(id), CategoryViewDeleteModel.class);

        modelAndView.addObject("category", categoryViewDeleteModel);

        return super.view("/category/delete-category", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteCategoryConfirm(@PathVariable String id) {
        this.categoryService
                .deleteCategory(id);

        return super.redirect("/categories/all");
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public List<CategoryViewModel> fetchCategories() {
        List<CategoryViewModel> categories = this.categoryService
                .findAllCategories()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryViewModel.class))
                .collect(Collectors.toList());
        return categories;
    }


}
