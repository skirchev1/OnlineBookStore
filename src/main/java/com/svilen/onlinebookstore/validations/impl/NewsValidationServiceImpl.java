package com.svilen.onlinebookstore.validations.impl;

import com.svilen.onlinebookstore.domain.entities.News;
import com.svilen.onlinebookstore.validations.NewsValidationService;
import org.springframework.stereotype.Component;

@Component
public class NewsValidationServiceImpl implements NewsValidationService {
    @Override
    public boolean isValid(News news) {
        return news!= null;
    }
}
