package com.svilen.onlinebookstore.service;

import com.svilen.onlinebookstore.domain.entities.News;
import com.svilen.onlinebookstore.domain.models.service.NewsServiceModel;
import com.svilen.onlinebookstore.repository.NewsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NewsServiceTest {

    @Autowired
    private NewsService service;

    @MockBean
    private NewsRepository mockNewsRepository;

    @Test
    public void getAll_whenThereAreNews_shouldReturnAllNews() {

        List<News> news = new ArrayList<>();
        news.add(new News());
        news.add(new News());


        when(mockNewsRepository.findAll())
                .thenReturn(news);

        List<NewsServiceModel> newsServiceModels = service.findAllNews();

        assertEquals(2, newsServiceModels.size());
    }


    @Test(expected = IllegalArgumentException.class)
    public void createNews_whenNull_throw() throws Exception {
        service.addNews(null);
        Mockito.verify(mockNewsRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void newsService_findNewsByIdWithInvalidValue_ThrowError() {
        service.findNewsById(null);
        Mockito.verify(mockNewsRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void newsService_editNewsWithInvalidValue_ThrowError() {
        NewsServiceModel news = new NewsServiceModel();
        service.editNews(null, news);
        Mockito.verify(mockNewsRepository)
                .save(any());
    }

}
