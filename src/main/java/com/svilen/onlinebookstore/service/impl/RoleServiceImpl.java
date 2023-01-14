package com.svilen.onlinebookstore.service.impl;

import com.svilen.onlinebookstore.domain.entities.Role;
import com.svilen.onlinebookstore.domain.models.service.RoleServiceModel;
import com.svilen.onlinebookstore.repository.RoleRepository;
import com.svilen.onlinebookstore.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDB() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.saveAndFlush(new Role("ROLE_USER"));
            this.roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
        }
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository
                .findAll()
                .stream()
                .map(role -> this.modelMapper.map(role, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return this.modelMapper
                .map(this.roleRepository.findByAuthority(authority),
                        RoleServiceModel.class);
    }
}
