package com.svilen.onlinebookstore.validations;

import com.svilen.onlinebookstore.domain.entities.News;

public interface NewsValidationService {
    boolean isValid(News news);
}
