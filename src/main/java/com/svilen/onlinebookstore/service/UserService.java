package com.svilen.onlinebookstore.service;

import com.svilen.onlinebookstore.domain.models.service.UserServiceModel;
import com.svilen.onlinebookstore.error.UsernameAlreadyExistException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserServiceModel userServiceModel) throws UsernameAlreadyExistException;

    UserServiceModel findUserByUsername(String username);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword);

    List<UserServiceModel> findAllUsers();

    void setUserRole(String id, String role);
}
