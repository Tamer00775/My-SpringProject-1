package ru.alishev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.dao.BookDAO;
import ru.alishev.dao.PersonDAO;
import ru.alishev.dao.PersonMapper;
import ru.alishev.models.Book;
import ru.alishev.models.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO){
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book")Book book){
        return "/books/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute(bookDAO.show(id));
        return "/books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book")Book book, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "books/edit";
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        Person bperson = bookDAO.showPerson(id);
        Person person = new Person();
        System.out.println(bookDAO.show(id).getPerson_id());
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("people", personDAO.index());
        model.addAttribute("person", person);
        model.addAttribute("bperson", bperson);
        return "/books/show";
    }

    @PatchMapping ("{id}/add")
    public String makePerson(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        System.out.println("person id after submit: " + person.getId() + "BOOK ID: " + id);
        bookDAO.addPeople(person.getId(), id);
        return "redirect:/books";
    }

    @DeleteMapping("{id}/delete")
    public String deletePerson(@ModelAttribute("person")Person person, @PathVariable("id") int id){
        bookDAO.deletePeople(id);
        return "redirect: /books";
    }
}