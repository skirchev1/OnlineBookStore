package com.svilen.onlinebookstore.service;

import com.svilen.onlinebookstore.domain.entities.Book;
import com.svilen.onlinebookstore.domain.models.service.BookServiceModel;
import com.svilen.onlinebookstore.domain.models.service.CategoryServiceModel;
import com.svilen.onlinebookstore.error.BookNotFoundException;
import com.svilen.onlinebookstore.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceTests {

    @InjectMocks
    Book book;

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository mockBookRepository;

    @Test
    public void createBook_whenValid_bookCreated() throws Exception {
        BookServiceModel book = new BookServiceModel();
        book.setCategories(List.of(new CategoryServiceModel()));
        Mockito.when(mockBookRepository.save(any()))
                .thenReturn(new Book());

        bookService.addBook(book);
        Mockito.verify(mockBookRepository)
                .save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createBook_whenNull_throw() throws Exception {
        bookService.addBook(null);
        Mockito.verify(mockBookRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void bookService_findBookByIdWithInvalidValue_ThrowError() {
        bookService.findBookById(null);
        Mockito.verify(mockBookRepository)
                .save(any());
    }

    @Test(expected = BookNotFoundException.class)
    public void bookService_deleteBookWithInvalidValue_ThrowError() {
        bookService.deleteBook(null);
        Mockito.verify(mockBookRepository)
                .saveAndFlush(any());
    }

    @Test(expected = Exception.class)
    public void bookService_editBookWithInvalidValue_ThrowError() {
        bookService.editBook(null, null);
        Mockito.verify(mockBookRepository)
                .save(any());
    }


}
