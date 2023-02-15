package ru.alishev.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alishev.models.Book;
import ru.alishev.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM BOOK", new BeanPropertyRowMapper<>(Book.class));
    }
   public Person showPerson(int id){
       return jdbcTemplate.query("SELECT * FROM Person WHERE person.id=(select person_id from book where id=?)", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }
    public void save(Book book){
        jdbcTemplate.update("INSERT INTO BOOK(name, author, year) values(?, ?, ?)",book.getName(), book.getAuthor(), book.getYear());
    }
    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM Book where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void update(int id, Book book){
        jdbcTemplate.update("Update Book set name=?, author=?, year=? where id=?", book.getName(), book.getAuthor(), book.getYear(), book.getId());
    }

    public void delete(int id){
        jdbcTemplate.update("Delete from Book where id=?", id);
    }
    public void deletePeople(int id){
        jdbcTemplate.update("Update Book set person_id = null where id=?", id);
    }
    public boolean checkPerson(int id){
        Book book = show(id);
        if(book == null)
            return false;
        else
            return true;
    }
    public void addPeople(int personId, int bookId){
        jdbcTemplate.update("Update Book set person_id =? where id=?", personId , bookId);
    }

}
