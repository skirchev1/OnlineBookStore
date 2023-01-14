package com.svilen.onlinebookstore.service.impl;

import com.svilen.onlinebookstore.domain.entities.User;
import com.svilen.onlinebookstore.domain.models.service.UserServiceModel;
import com.svilen.onlinebookstore.error.UsernameAlreadyExistException;
import com.svilen.onlinebookstore.repository.UserRepository;
import com.svilen.onlinebookstore.service.RoleService;
import com.svilen.onlinebookstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) throws UsernameAlreadyExistException {
        this.roleService.seedRolesInDB();

        if (this.userRepository.count() == 0) {
            userServiceModel.setAuthorities(new LinkedHashSet<>());
                    userServiceModel.getAuthorities()
                    .add(this.roleService.findByAuthority("ROLE_ADMIN"));
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel
                    .getAuthorities()
                    .add(this.roleService.findByAuthority("ROLE_USER"));
        }

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        return this.modelMapper
                .map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        return this.userRepository
                .findByUsername(username)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    public UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword) {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));

        if (!this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password!");
        }

        user.setPassword(!"".equals(userServiceModel.getPassword())
                ? userServiceModel.getPassword()
                : user.getPassword()
        );

        user.setEmail(!"".equals(userServiceModel.getEmail())
                ? userServiceModel.getEmail()
                : user.getEmail()
        );

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository
                .findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void setUserRole(String id, String role) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserServiceModel userServiceModel =
                this.modelMapper.map(user, UserServiceModel.class);

        userServiceModel.getAuthorities().clear();

        switch (role) {
            case "user":
                userServiceModel.getAuthorities()
                        .add(this.roleService.findByAuthority("ROLE_USER"));
                break;
            case "admin":
                userServiceModel.getAuthorities()
                        .add(this.roleService.findByAuthority("ROLE_USER"));
                userServiceModel.getAuthorities()
                        .add(this.roleService.findByAuthority("ROLE_ADMIN"));
                break;
        }

        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
    }
}
