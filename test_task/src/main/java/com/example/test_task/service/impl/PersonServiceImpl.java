package com.example.test_task.service.impl;

import com.example.test_task.persistence.entity.Person;
import com.example.test_task.persistence.repository.PersonRepository;
import com.example.test_task.service.PersonService;

import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person findById(Long id) {
        return personRepository.findById(id).get();
    }
}