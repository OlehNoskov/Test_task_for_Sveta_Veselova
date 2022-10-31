package com.example.test_task.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table (name = "persons")
@EqualsAndHashCode (callSuper = true)
public class Person extends BaseEntity {

    @Column(name = "first_name")
    String first_name;

    @Column(name = "last_name")
    String last_name;

    @Column(name = "age")
    int age;

    public Person() {
        super();
    }
}