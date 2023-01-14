package com.svilen.onlinebookstore.validations.impl;

import com.svilen.onlinebookstore.domain.entities.User;
import com.svilen.onlinebookstore.domain.models.service.UserServiceModel;
import com.svilen.onlinebookstore.validations.UserValidationService;
import org.springframework.stereotype.Component;

@Component
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isValid(UserServiceModel userServiceModel) {
        return userServiceModel != null;
    }


    @Override
    public boolean isValid(User user) {
        return user != null;
    }
}
