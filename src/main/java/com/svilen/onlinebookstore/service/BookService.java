package com.svilen.onlinebookstore.service;

import com.svilen.onlinebookstore.domain.models.service.BookServiceModel;
import com.svilen.onlinebookstore.error.BookNotFoundException;

import java.util.List;

public interface BookService {
    BookServiceModel addBook(BookServiceModel bookServiceModel) throws Exception;

    List<BookServiceModel> findAllBooks();

    BookServiceModel findBookById(String id) throws BookNotFoundException;

    BookServiceModel editBook(String id, BookServiceModel bookServiceModel) throws BookNotFoundException;

    void deleteBook(String id);

}
