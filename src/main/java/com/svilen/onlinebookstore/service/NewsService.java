package com.svilen.onlinebookstore.service;

import com.svilen.onlinebookstore.domain.models.service.NewsServiceModel;
import com.svilen.onlinebookstore.error.NewsInvalidNameException;

import java.util.List;

public interface NewsService {

    NewsServiceModel addNews(NewsServiceModel newsServiceModel) throws Exception;

    List<NewsServiceModel> findAllNews();

    NewsServiceModel findNewsById(String id) throws NewsInvalidNameException;

    NewsServiceModel editNews(String id, NewsServiceModel newsServiceModel) throws NewsInvalidNameException;

    void deleteNews(String id);
}
