package com.svilen.onlinebookstore.controller;

import com.svilen.onlinebookstore.web.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HomeControllerTest {
    @Autowired
    private HomeController homeController;

    @Test
    public void contextLoad() throws Exception {
        assertThat(homeController).isNotNull();
    }


}
