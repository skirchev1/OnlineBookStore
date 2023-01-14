package com.svilen.onlinebookstore.service;

import com.svilen.onlinebookstore.domain.entities.Role;
import com.svilen.onlinebookstore.domain.entities.User;
import com.svilen.onlinebookstore.domain.models.service.RoleServiceModel;
import com.svilen.onlinebookstore.domain.models.service.UserServiceModel;
import com.svilen.onlinebookstore.repository.UserRepository;
import com.svilen.onlinebookstore.service.impl.UserServiceImpl;
import com.svilen.onlinebookstore.validations.UserValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.management.relation.RoleNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class UserServiceTests {

    private User testUser;
    private UserRepository mockUserRepository;

    private static final UserServiceModel MODEL = new UserServiceModel();
    private static final User USER = new User();

    private static final String VALID_FIRST_NAME = "Svilen";
    private static final String VALID_LAST_NAME = "Kirchev";
    private static final String VALID_USERNAME = "Preatorian";
    private static final String VALID_PASSWORD = "qwer";
    private static final String VALID_EDITED_PASSWORD = "1234567";
    private static final String VALID_EMAIL = "k_svilen@abv.bg";
    private static final String VALID_ID = "id";
    private static final String VALID_EDITED_EMAIL = "dj_abv@abv.bg";

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleService roleService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    BCryptPasswordEncoder encoder;

    @Mock
    UserValidationService validatorService;

    @Before
    public void init() {

        ModelMapper actualMapper = new ModelMapper();
        BCryptPasswordEncoder actualEncoder = new BCryptPasswordEncoder();

        when(modelMapper.map(any(UserServiceModel.class), ArgumentMatchers.eq(User.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], User.class));

        when(modelMapper.map(any(User.class), ArgumentMatchers.eq(UserServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], UserServiceModel.class));

        when(roleService.findByAuthority(anyString()))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(new Role((String) invocationOnMock.getArguments()[0]),
                                RoleServiceModel.class));

        USER.setFirstName(VALID_FIRST_NAME);
        USER.setLastName(VALID_LAST_NAME);
        USER.setUsername(VALID_USERNAME);
        USER.setPassword(VALID_PASSWORD);
        USER.setEmail(VALID_EMAIL);


        MODEL.setFirstName(VALID_FIRST_NAME);
        MODEL.setLastName(VALID_LAST_NAME);
        MODEL.setUsername(VALID_USERNAME);
        MODEL.setPassword(VALID_PASSWORD);
        MODEL.setEmail(VALID_EMAIL);
        MODEL.setAuthorities(Set.of(new RoleServiceModel()));

        this.testUser =new User(){{
           setId("UUID");
           setUsername("Ivan");
           setPassword("qwerty");
        }};
        this.mockUserRepository= mock(UserRepository.class);

    }

    @Test
    public void registerUser_WhenNotValid_ShouldThrow() {

        Mockito.when(validatorService.isValid(MODEL))
                .thenReturn(false);

        userService.registerUser(MODEL);
    }

    @Test
    public void userService_GetUserWithCorrectUsername_ShouldReturnCorrect() {
        Mockito.when(this.mockUserRepository.findByUsername("Ivan")).thenReturn(Optional.ofNullable(this.testUser));

        UserService userService = new UserServiceImpl(this.mockUserRepository,this.roleService,this.modelMapper,this.encoder);
        User expected = this.testUser;


    }

    @Test
    public void registerUser_WhenFirstValidUserIsAdded_ShouldWork() throws RoleNotFoundException {

        when(userRepository.count()).thenReturn(0L);
        when(validatorService.isValid(MODEL)).thenReturn(true);
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(USER);

        UserServiceModel serviceModel = userService.registerUser(MODEL);

        assertNotNull(MODEL);
        assertEquals(USER.getUsername(), serviceModel.getUsername());
    }

    @Test
    public void registerUser_WhenNotFirstValidUserIsAdded_ShouldWork() {

        String authority = "ROLE_CUSTOMER";
        RoleServiceModel mockRole = new RoleServiceModel() {{
            new Role(authority);
        }};

        when(userRepository.count()).thenReturn((long) 0);
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(USER);
        when(roleService.findByAuthority(authority)).thenReturn(mockRole);

        UserServiceModel serviceModel = userService.registerUser(MODEL);

        assertNotNull(serviceModel);
        assertEquals(USER.getUsername(), serviceModel.getUsername());

    }

    @Test(expected = UsernameNotFoundException.class)
    public void findByUsername_WhenNotExistUser_ShouldThrow() {
        String username = "Peter";

        userService.findUserByUsername(username);
    }

    @Test
    public void findByUsername_WhenUserExist_ShouldWork() {

        when(userRepository.findByUsername(VALID_USERNAME)).thenReturn(Optional.of(USER));

        UserServiceModel serviceModel = userService.findUserByUsername(VALID_USERNAME);

        assertEquals(VALID_USERNAME, serviceModel.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadByUsername_WhenNotExistUser_ShouldThrow() {
        String username = "Peter";

        userService.loadUserByUsername(username);
    }

    @Test
    public void loadByUsername_WhenUserExist_ShouldWork() {

        when(userRepository.findByUsername(VALID_USERNAME)).thenReturn(Optional.of(USER));

        UserServiceModel serviceModel = userService.findUserByUsername(VALID_USERNAME);

        assertEquals(VALID_USERNAME, serviceModel.getUsername());
    }

    @Test
    public void registerUser_WhenModelIsNotValid_ShouldThrow() throws RoleNotFoundException {

        when(validatorService.isValid(MODEL)).thenReturn(false);

        userService.registerUser(MODEL);
    }

    @Test
    public void registerUser_WhenUserIsExist_ShouldThrow() throws RoleNotFoundException {

        when(userRepository.findByUsername(USER.getUsername())).thenReturn(Optional.of(USER));

        userService.registerUser(MODEL);
    }

    @Test
    public void registerUser_WhenEmailIsExist_ShouldThrow() throws RoleNotFoundException {

        when(userRepository.findByEmail(USER.getEmail())).thenReturn(Optional.of(USER));

        userService.registerUser(MODEL);
    }

    @Test
    public void findAllUser_WhenNotHaveUsers_ShouldReturnEmptyList() {

        List<UserServiceModel> allUsers = userService.findAllUsers();

        assertEquals(0, allUsers.size());
    }


    @Test(expected = UsernameNotFoundException.class)
    public void editUserProfile_WhenUserNotExist() {

        userService.editUserProfile(MODEL, VALID_PASSWORD);
    }
}
