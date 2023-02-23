package ru.alishev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.models.Book;
import ru.alishev.models.Person;
import ru.alishev.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book find(int id){
        Optional<Book> bookOptional = bookRepository.findById(id);
        return bookOptional.orElse(null) ;
    }
    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }
    @Transactional
    public void update(int id, Book book){
        book.setId(id);
        bookRepository.save(book);
    }
    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }
    public List<Book> findByOwner(Person person){
        return bookRepository.findByOwner_Id(person.getId());
    }


}
