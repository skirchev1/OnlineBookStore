package com.svilen.onlinebookstore.service.impl;


import com.svilen.onlinebookstore.domain.entities.Book;
import com.svilen.onlinebookstore.domain.entities.Category;
import com.svilen.onlinebookstore.domain.models.service.BookServiceModel;
import com.svilen.onlinebookstore.error.BookNotFoundException;
import com.svilen.onlinebookstore.repository.BookRepository;
import com.svilen.onlinebookstore.service.BookService;
import com.svilen.onlinebookstore.validations.BookValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final BookValidationService bookValidationService;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper, @Qualifier("bookValidationServiceImpl") BookValidationService bookValidationService) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.bookValidationService = bookValidationService;
    }

    @Override
    public BookServiceModel addBook(BookServiceModel bookServiceModel){
        Book book = this.modelMapper.map(bookServiceModel, Book.class);

        if (!this.bookValidationService.isValid(book)){
            throw new IllegalArgumentException();
        }
        this.bookRepository.save(book);
        return bookServiceModel;
    }

    @Override
    public List<BookServiceModel> findAllBooks() {
        return this.bookRepository.findAll().stream().map(b -> this.modelMapper.map(b, BookServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public BookServiceModel findBookById(String id) throws BookNotFoundException {
        return this.bookRepository.findById(id).map(b -> this.modelMapper.map(b, BookServiceModel.class))
                .orElseThrow(() -> new BookNotFoundException("Book with this id was not found"));
    }

    @Override
    public BookServiceModel editBook(String id, BookServiceModel bookServiceModel) throws BookNotFoundException {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with this id was not found"));

        book.setName(bookServiceModel.getName());
        book.setDescription(bookServiceModel.getDescription());
        book.setPrice(bookServiceModel.getPrice());
        book.setCategories(bookServiceModel.getCategories().stream().map(c -> this.modelMapper.map(c, Category.class)).collect(Collectors.toList()));

        return this.modelMapper.map(this.bookRepository.saveAndFlush(book), BookServiceModel.class);
    }

    @Override
    public void deleteBook(String id) {
            this.bookRepository.deleteById(id);
    }

}
