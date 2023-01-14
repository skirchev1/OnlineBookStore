package com.svilen.onlinebookstore.validations;

import com.svilen.onlinebookstore.constants.BookConstants;
import com.svilen.onlinebookstore.domain.models.binding.BookAddBindingModel;
import com.svilen.onlinebookstore.repository.BookRepository;
import org.springframework.validation.Errors;


@Validator
public class BookAddValidator implements org.springframework.validation.Validator{

    private final BookRepository bookRepository;

    public BookAddValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return BookAddBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        BookAddBindingModel bookAddBindingModel = (BookAddBindingModel) o;

        if (this.bookRepository.findByName(bookAddBindingModel.getName()).isPresent()) {
            errors.rejectValue(
                    "name",
                    String.format(BookConstants.BOOK_NAME_ALREADY_EXIST, bookAddBindingModel.getName()),
                    String.format(BookConstants.BOOK_NAME_ALREADY_EXIST, bookAddBindingModel.getName())
            );
        }

        if (bookAddBindingModel.getName().length() < 6) {
            errors.rejectValue(
                    "name",
                    BookConstants.BOOK_NAME_LENGTH_NOT_CORRECT,
                    BookConstants.BOOK_NAME_LENGTH_NOT_CORRECT
            );
        }

        if (bookAddBindingModel.getDescription().length() < 10) {
            errors.rejectValue(
                    "text",
                    BookConstants.DESCRIPTION_LENGTH_NOT_CORRECT,
                    BookConstants.DESCRIPTION_LENGTH_NOT_CORRECT
            );
        }
    }
}
