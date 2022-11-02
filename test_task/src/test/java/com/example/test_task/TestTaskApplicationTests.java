package com.example.test_task;

import com.example.test_task.controller.BaseController;
import com.example.test_task.persistence.entity.Person;
import com.example.test_task.persistence.repository.PersonRepository;
import com.example.test_task.service.PersonService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class TestTaskApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    private final long testUserId = 10;

    @Test
    public void whenFindByIdName_successfully() {
        String name = personService.findById(testUserId).getFirst_name();
        Assertions.assertEquals("Dmitriy", name);
    }

    @Test
    public void wheFindByIdSurname_successfully() {
        String surname = personService.findById(testUserId).getLast_name();
        Assertions.assertEquals("Shelestov", surname);
    }

    @Test
    public void testCalculateAge_successfully() {
        Person person = personService.findById(testUserId);
        Assertions.assertEquals(21, person.getAge());
    }

    @Test
    public void testCalculateAge_failed() {
        Person person = new Person();
        long id = personRepository.saveAndFlush(person).getId();

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            personService.findById(id).getBirthDay();
        });
        Assertions.assertEquals("Birthday null!", thrown.getMessage());
        personRepository.delete(person);
    }

    @Test
    void testExpectedExceptionFindById() {
        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            personService.findById(20L);
        });
        Assertions.assertEquals("Person does not exist for id = 20!", thrown.getMessage());
    }

    @Test
    public void givenPerson_whenGetPerson_thenStatus200() {
        long id = personService.findById(10L).getId();
        HttpEntity<Person> entity = new HttpEntity<>(personService.findById(testUserId));
        Person person = testRestTemplate.getForObject("/persons/search/{id}", Person.class, id);
        ResponseEntity<Person> response = testRestTemplate.exchange("/persons/search/{id}", HttpMethod.GET, entity, Person.class, id);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(person.getFirst_name(), is("Dmitriy"));
        assertThat(person.getLast_name(), is("Shelestov"));
        assertThat(person.getAge(), is(21));
    }

    @Test
    public void testControllerFindPersonById_thenStatus200() throws Exception {
        ResultMatcher ok = MockMvcResultMatchers.status().is2xxSuccessful();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/persons/search/" + testUserId);
        this.mvc.perform(builder).andExpect(ok);
    }
}