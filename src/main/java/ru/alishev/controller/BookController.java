package ru.alishev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.alishev.models.Book;
import ru.alishev.models.Person;
import ru.alishev.service.BookService;
import ru.alishev.service.PeopleService;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final PeopleService peopleService;
    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookService.findAll());
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
        bookService.save(book);
        return "redirect:/books";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute(bookService.find(id));
        return "/books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book")Book book, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "books/edit";
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        Person bperson = bookService.find(id).getOwner();
        Person person = new Person();
        model.addAttribute("book", peopleService.findBooksByPersonId(id));
        model.addAttribute("people", peopleService.findAll());
        model.addAttribute("person", person);
        model.addAttribute("bperson", bperson);
        return "/books/show";
    }

    @PatchMapping ("{id}/add")
    public String makePerson(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        System.out.println("person id after submit: " + person.getId() + "BOOK ID: " + id);
       //  bookDAO.addPeople(person.getId(), id);
        bookService.find(id).setOwner(person);
        return "redirect:/books";
    }

    @DeleteMapping("{id}/delete")
    public String deletePerson(@ModelAttribute("person")Person person, @PathVariable("id") int id){
        bookService.find(id).setOwner(null);
        return "redirect: /books";
    }
}