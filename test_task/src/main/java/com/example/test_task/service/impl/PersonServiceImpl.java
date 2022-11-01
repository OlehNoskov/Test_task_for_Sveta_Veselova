package com.example.test_task.service.impl;

import com.example.test_task.persistence.entity.Person;
import com.example.test_task.persistence.repository.PersonRepository;
import com.example.test_task.service.PersonService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person findById(Long id) {
        Person person = personRepository.findById(id).get();
        person.setAge(Period.between(convertToLocalDateViaSqlDate(person.getBirthDay()),
                                     convertToLocalDateViaSqlDate(new Date(System.currentTimeMillis()))).getYears());
        return person;
    }

    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }
}