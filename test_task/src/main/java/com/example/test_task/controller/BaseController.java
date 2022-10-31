package com.example.test_task.controller;

import com.example.test_task.persistence.entity.Person;
import com.example.test_task.service.PersonService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class BaseController {
    private final PersonService personService;

    public BaseController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/search/{id}")
    public Person findPersonById(@PathVariable Long id) {
        return personService.findById(id);
    }
}