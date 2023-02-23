package ru.alishev.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.models.Book;
import ru.alishev.models.Person;
import ru.alishev.repository.PersonRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PersonRepository personRepository;
    @Autowired
    public PeopleService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public List<Person> findAll(){
        return personRepository.findAll();
    }
    public Person findOne(int id){
        Optional<Person> findedPerson = personRepository.findById(id);
        return findedPerson.orElse(null);
    }

    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }
    @Transactional
    public void update(int id, Person person){
        person.setId(id);
        personRepository.save(person);
    }
    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }

    public List<Book> findBooksByPersonId(int id){
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        }
        else {
            return Collections.emptyList();
        }
    }

}
