package service;

import com.akzhol.dao.PersonDAO;
import com.akzhol.enums.CommandType;
import com.akzhol.model.Command;
import com.akzhol.model.Person;
import com.akzhol.service.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServiceTest {

    @Mock
    private PersonDAO personDAO;

    @InjectMocks
    private ObjectMapper objectMapper;

    private Service service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new Service(objectMapper, personDAO);
    }

    @Test
    public void shouldCreatePersonWhenValidDataProvided(){
//        Given
        Person person = new Person(null, "Test", 45);
        Command command = new Command(CommandType.CREATE, null, person);

//        When
       Integer personId =  service.createPerson(command);

//       Then
       assertNotNull(personId);

    }

    @Test
    void shouldDeletePersonWhenPersonExists() {
        // Given
        Integer personId = 1;
        Person person = new Person(null, "John Doe", 30);
        Command command = new Command(CommandType.DELETE, personId, null);

        when(personDAO.getPersonById(personId)).thenReturn(person);

        // When
        service.deletePerson(command);

        // Then
        verify(personDAO).getPersonById(personId);
        verify(personDAO).deletePersonById(personId);
    }


    @Test
    void shouldNotDeletePersonWhenPersonDoesNotExist() {
        // Given
        Integer personId = 1;
        Command command = new Command(CommandType.DELETE, personId, null);

        when(personDAO.getPersonById(personId)).thenReturn(null);

        // When
        service.deletePerson(command);

        // Then
        verify(personDAO).getPersonById(personId);
        verify(personDAO, never()).deletePersonById(anyInt());
    }


    @Test
    void shouldReturnAllPersonsWhenCommandIdIsNull() throws JsonProcessingException {
        // Given
        Command command = new Command(CommandType.GET, null, null);
        List<Person> persons = Arrays.asList(new Person(1L, "John", 30), new Person(2L, "Jane", 25));
        String expectedJson = "[{\"id\":1,\"name\":\"John\",\"age\":30},{\"id\":2,\"name\":\"Jane\",\"age\":25}]";

        when(personDAO.getAllPerson()).thenReturn(persons);


        // When
        String result = service.getPerson(command);

        // Then
        assertEquals(expectedJson, result);
        verify(personDAO).getAllPerson();

    }

    @Test
    void shouldReturnEmptyWhenNoPersonsFound() {
        // Given
        Command command = new Command(CommandType.GET, null, null);
        when(personDAO.getAllPerson()).thenReturn(Collections.emptyList());

        // When
        String result = service.getPerson(command);

        // Then
        assertNull(result);
        verify(personDAO).getAllPerson();

    }

    @Test
    void shouldReturnNullWhenSpecificPersonNotFound() {
        // Given
        Integer personId = 1;
        Command command = new Command(CommandType.GET, personId, null);
        when(personDAO.getPersonById(personId)).thenReturn(null);

        // When
        String result = service.getPerson(command);

        // Then
        assertNull(result);
        verify(personDAO).getPersonById(personId);

    }

    @Test
    void shouldUpdatePersonWhenPersonExists() {
        // Given
        Integer personId = 1;
        Person existingPerson = new Person(1L, "John Doe", 30);
        Person updatedPerson = new Person(1L, "John Updated", 31);
        Command command = new Command(CommandType.UPDATE, personId, updatedPerson);

        when(personDAO.getPersonById(personId)).thenReturn(existingPerson);

        // When
        service.updatePerson(command);

        // Then
        verify(personDAO).getPersonById(personId);
        verify(personDAO).updatePerson(updatedPerson, personId);
    }

    @Test
    void shouldNotUpdatePersonWhenPersonDoesNotExist() {
        // Given
        Integer personId = 1;
        Person updatedPerson = new Person(1L, "John Updated", 31);
        Command command = new Command(CommandType.UPDATE, personId, updatedPerson);

        when(personDAO.getPersonById(personId)).thenReturn(null);

        // When
        service.updatePerson(command);

        // Then
        verify(personDAO).getPersonById(personId);
        verify(personDAO, never()).updatePerson(any(Person.class), anyInt());
    }


}
