package com.example.test_task.service;

import com.example.test_task.persistence.entity.Person;

public interface PersonService {
    Person findById(Long id);
}