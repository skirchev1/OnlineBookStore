package com.svilen.onlinebookstore.service;

import com.svilen.onlinebookstore.domain.models.service.CategoryServiceModel;
import com.svilen.onlinebookstore.error.CategoryInvalidNameException;

import java.util.List;

public interface CategoryService {
    CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel) throws CategoryInvalidNameException;

    List<CategoryServiceModel> findAllCategories();

    CategoryServiceModel findById(String id);

    CategoryServiceModel editCategory(String id, CategoryServiceModel categoryServiceModel);

    void deleteCategory(String id);

}
