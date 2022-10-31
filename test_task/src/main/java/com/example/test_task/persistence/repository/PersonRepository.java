package com.example.test_task.persistence.repository;

import com.example.test_task.persistence.entity.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends AbstractRepository <Person>{
}