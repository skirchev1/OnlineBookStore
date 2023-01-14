package com.svilen.onlinebookstore.validations;

import com.svilen.onlinebookstore.constants.NewsConstants;
import com.svilen.onlinebookstore.domain.models.binding.NewsAddBindingModel;
import com.svilen.onlinebookstore.repository.NewsRepository;
import org.springframework.validation.Errors;

@Validator
public class NewsAddValidator implements org.springframework.validation.Validator{

    private final NewsRepository newsRepository;

    public NewsAddValidator(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return NewsAddBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        NewsAddBindingModel newsAddBindingModel = (NewsAddBindingModel) o;

        if (this.newsRepository.findByName(newsAddBindingModel.getName()).isPresent()) {
            errors.rejectValue(
                    "name",
                    String.format(NewsConstants.NEWS_NAME_ALREADY_EXIST, newsAddBindingModel.getName()),
                    String.format(NewsConstants.NEWS_NAME_ALREADY_EXIST, newsAddBindingModel.getName())
            );
        }

        if (newsAddBindingModel.getName().length() < 6) {
            errors.rejectValue(
                    "name",
                    NewsConstants.NEWS_NAME_LENGTH_NOT_CORRECT,
                    NewsConstants.NEWS_NAME_LENGTH_NOT_CORRECT
            );
        }

        if (newsAddBindingModel.getText().length() < 10) {
            errors.rejectValue(
                    "text",
                    NewsConstants.TEXT_LENGTH_NOT_CORRECT,
                    NewsConstants.TEXT_LENGTH_NOT_CORRECT
            );
        }
    }
}
