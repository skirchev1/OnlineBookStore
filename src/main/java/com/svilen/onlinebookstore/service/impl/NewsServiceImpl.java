package com.svilen.onlinebookstore.service.impl;

import com.svilen.onlinebookstore.domain.entities.News;
import com.svilen.onlinebookstore.domain.models.service.NewsServiceModel;
import com.svilen.onlinebookstore.error.NewsInvalidNameException;
import com.svilen.onlinebookstore.error.NewsNotFoundException;
import com.svilen.onlinebookstore.repository.NewsRepository;
import com.svilen.onlinebookstore.service.NewsService;
import com.svilen.onlinebookstore.validations.BookValidationService;
import com.svilen.onlinebookstore.validations.NewsValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper;
    private final NewsValidationService newsValidationService;

    public NewsServiceImpl(NewsRepository newsRepository, ModelMapper modelMapper, NewsValidationService newsValidationService) {
        this.newsRepository = newsRepository;
        this.modelMapper = modelMapper;
        this.newsValidationService = newsValidationService;
    }

    @Override
    public NewsServiceModel addNews(NewsServiceModel newsServiceModel){

        News news = this.modelMapper.map(newsServiceModel, News.class);

        if (!this.newsValidationService.isValid(news)){
            throw new IllegalArgumentException();
        }

        this.newsRepository.save(news);

        return newsServiceModel;
    }

    @Override
    public List<NewsServiceModel> findAllNews() {
        return this.newsRepository.findAll().stream().map(n -> this.modelMapper.map(n, NewsServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public NewsServiceModel findNewsById(String id) throws NewsInvalidNameException {
        return this.newsRepository.findById(id).map(n -> this.modelMapper.map(n, NewsServiceModel.class))
                .orElseThrow(() -> new NewsNotFoundException("News with this id was not found"));
    }

    @Override
    public NewsServiceModel editNews(String id, NewsServiceModel newsServiceModel) throws NewsInvalidNameException {
        News news = this.newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("News with this id was not found"));

        news.setName(newsServiceModel.getName());
        news.setText(newsServiceModel.getText());

        return this.modelMapper.map(this.newsRepository.saveAndFlush(news), NewsServiceModel.class);
    }

    @Override
    public void deleteNews(String id) {
        this.newsRepository.deleteById(id);
    }
}
