package ru.alishev.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import ru.alishev.models.Book;

import java.util.List;

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

}
