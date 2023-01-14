package com.svilen.onlinebookstore.service;

import com.svilen.onlinebookstore.domain.entities.Role;
import com.svilen.onlinebookstore.domain.models.service.RoleServiceModel;
import com.svilen.onlinebookstore.repository.RoleRepository;
import com.svilen.onlinebookstore.service.impl.RoleServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.relation.RoleNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RoleServiceTest {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";
    private static final Role TEST_ROLE = new Role(ROLE_USER);

    private static List<Role> fakeRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    @Before
    public void init() {

        fakeRepository = new ArrayList<>();

        when(roleRepository.saveAndFlush(any(Role.class)))
                .thenAnswer(invocationOnMock -> {
                    fakeRepository.add((Role) invocationOnMock.getArguments()[0]);
                    return null;
                });

        ModelMapper actualMapper = new ModelMapper();
        when(modelMapper.map(any(Role.class), ArgumentMatchers.eq(RoleServiceModel.class)))
                .thenAnswer(invocationOnMock -> actualMapper.map(invocationOnMock.getArguments()[0], RoleServiceModel.class));
    }

    @Test
    public void seedRoles_shouldSeedAllRoles_whenRepositoryEmpty() {


        when(roleRepository.count()).thenReturn(0L);

        roleService.seedRolesInDB();

        fakeRepository = fakeRepository.stream()
                .sorted(Comparator.comparing(Role::getAuthority)).collect(Collectors.toList());

        int expected = 2;
        int actual = fakeRepository.size();
        assertEquals(expected, actual);
        assertEquals(fakeRepository.get(0).getAuthority(), ROLE_ADMIN);
        assertEquals(fakeRepository.get(1).getAuthority(), ROLE_USER);
    }

    @Test
    public void seedRoles_shouldDoNothing_whenRepositoryNotEmpty() {

        when(roleRepository.count()).thenReturn(4L);

        roleService.seedRolesInDB();

        int expected = 0;
        int actual = fakeRepository.size();
        assertEquals(expected, actual);
    }


    @Test
    public void findAll_shouldReturnAllServiceModelsCorrect_whenAnyRolesInDb() {


        List<Role> allRoles = Arrays
                .asList( new Role(ROLE_ADMIN), new Role(ROLE_USER));
        when(roleRepository.findAll()).thenReturn(allRoles);


        Set<RoleServiceModel> allFoundRoleServiceModels = roleService.findAllRoles();


        int expected = allRoles.size();
        int actual = allFoundRoleServiceModels.size();
        assertEquals(expected, actual);
    }

    @Test
    public void findAll_shouldReturnEmptyCollection_whenNoRolesInDb() {


        List<Role> allRoles = new ArrayList<>();
        when(roleRepository.findAll()).thenReturn(allRoles);

        Set<RoleServiceModel> allFoundRoleServiceModels = roleService.findAllRoles();

        int expected = allRoles.size();
        int actual = allFoundRoleServiceModels.size();
        assertEquals(expected, actual);
    }

    @Test
    public void findRoleByAuthorityReturnRole_whenRoleExist() {

        when(roleRepository.findByAuthority(ROLE_USER)).thenReturn(TEST_ROLE);

        RoleServiceModel result = roleService.findByAuthority(ROLE_USER);

        assertEquals(ROLE_USER, result.getAuthority());
    }
}
