package ru.alishev.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Book {
    int id;
    @NotEmpty
    @Pattern(regexp = "[A-Z]\\w+", message = "Ex: Name")
    private String name;
    @NotEmpty(message = "Kartayev Tamerlan")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+")
    private String author;
    @Max(value=2022, message = "You not correct add book! Our book is till due 2022")
    private int year;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book(){

    }
    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
