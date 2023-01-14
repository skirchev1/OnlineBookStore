package com.svilen.onlinebookstore.validations;

import com.svilen.onlinebookstore.constants.UserConstants;
import com.svilen.onlinebookstore.domain.models.binding.UserRegisterBindingModel;
import com.svilen.onlinebookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

@Validator
public class UserRegisterValidator implements org.springframework.validation.Validator{

    private final UserRepository userRepository;

    @Autowired
    public UserRegisterValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegisterBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRegisterBindingModel userRegisterBindingModel = (UserRegisterBindingModel) o;

        if (this.userRepository.findByUsername(userRegisterBindingModel.getUsername()).isPresent()) {
            errors.rejectValue(
                    "username",
                    String.format(UserConstants.USERNAME_ALLREADY_EXIST, userRegisterBindingModel.getUsername()),
                    String.format(UserConstants.USERNAME_ALLREADY_EXIST, userRegisterBindingModel.getUsername())
            );
        }

        if (userRegisterBindingModel.getUsername().length() < 2 || userRegisterBindingModel.getUsername().length() > 10) {
            errors.rejectValue(
                    "username",
                    UserConstants.USERNAME_LENGTH_NOT_CORRECT,
                    UserConstants.USERNAME_LENGTH_NOT_CORRECT
            );
        }

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            errors.rejectValue(
                    "password",
                    UserConstants.PASSWORDS_NOT_MATCH,
                    UserConstants.PASSWORDS_NOT_MATCH
            );
        }

        if (this.userRepository.findByEmail(userRegisterBindingModel.getEmail()).isPresent()) {
            errors.rejectValue(
                    "email",
                    String.format(UserConstants.USER_WITH_EMAIL_ALLREADY_EXIST, userRegisterBindingModel.getEmail()),
                    String.format(UserConstants.USER_WITH_EMAIL_ALLREADY_EXIST, userRegisterBindingModel.getEmail())
            );
        }
    }

}
