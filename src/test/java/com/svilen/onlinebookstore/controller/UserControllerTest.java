package com.svilen.onlinebookstore.controller;

import com.svilen.onlinebookstore.domain.entities.User;
import com.svilen.onlinebookstore.repository.UserRepository;
import com.svilen.onlinebookstore.service.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @BeforeEach
    public void setup(){

        userRepository.deleteAll();

        User user = new User();

        roleService.seedRolesInDB();

        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Preatorian");
        user.setPassword("qwerty");
        user.setEmail("k_svilen@abv.bg");

        this.userRepository.saveAndFlush(user);
    }

    @AfterEach
    public void setDown(){
        this.userRepository.deleteAll();
    }

    @Test
    public void register_Page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register")).andExpect(status().isOk())
                .andExpect(view().name("register"));

    }

    @Test
    public void login_Page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
