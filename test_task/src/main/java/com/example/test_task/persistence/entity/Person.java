package com.example.test_task.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table (name = "persons")
@EqualsAndHashCode (callSuper = true)
public class Person extends BaseEntity {

    @JsonIgnore
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "birth_day")
    private Date birthDay;

    @Column(name = "first_name")
    String first_name;

    @Column(name = "last_name")
    String last_name;

    @Transient
    int age;

    public Person() {
        super();
    }
}