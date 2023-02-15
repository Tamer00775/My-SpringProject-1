package ru.alishev.util;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.dao.BookDAO;
import ru.alishev.models.Book;

import javax.validation.Valid;



public class BookValidator implements Validator {
    private BookDAO bookDAO;
    public BookValidator (BookDAO bookDAO){
        this.bookDAO = bookDAO;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        if(bookDAO.showPerson(book.getPerson_id()).equals(null))
            errors.rejectValue("person_id", "", "Not any person taken this book");
    }
}
