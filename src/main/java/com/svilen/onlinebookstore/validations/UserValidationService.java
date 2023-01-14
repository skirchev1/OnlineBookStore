package com.svilen.onlinebookstore.validations;

import com.svilen.onlinebookstore.domain.entities.User;
import com.svilen.onlinebookstore.domain.models.service.UserServiceModel;

public interface UserValidationService {
    boolean isValid(User user);

    boolean isValid(UserServiceModel userServiceModel);
}
