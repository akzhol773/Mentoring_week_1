package com.akzhol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Service {

    private final ObjectMapper objectMapper;
    private final PersonDAO personDAO;

    public Service(ObjectMapper objectMapper, PersonDAO personDAO) {
        this.objectMapper = objectMapper;
        this.personDAO = personDAO;
    }

    public void execute(Command command){
        switch (command.getCommandType()){
            case CREATE:
                createPerson(command);
                break;
            case GET:
                getPerson(command);
                break;
            case UPDATE:
                updatePerson(command);
                break;
            case DELETE:
                deletePerson(command);
                break;
            default:
                throw new InvalidCommandException("Invalid command");

        }
    }

    private void deletePerson(Command command) {
        int id = command.getId();
        var person = personDAO.getPersonById(id);
        if (person!= null){
            personDAO.deletePersonById(command.getId());
            System.out.printf("Person with id = %d deleted", id);
        }else {
            System.out.printf("No person found with id %d", id);
        }
    }

    private void updatePerson(Command command) {
        int id = command.getId();
        var person = personDAO.getPersonById(id);
        if (person!= null){
            personDAO.updatePerson(command.getPerson(), id);
            System.out.printf("Person with id = %d updated", id);
        }else {
            System.out.println("No person found with id "+ id);
        }
    }

    private String getPerson(Command command) {
        String jsonValue = null;
        try{
            if(command.getId()==null){
                var persons = personDAO.getAllPerson();
                if(!persons.isEmpty()){
                    jsonValue = objectMapper.writeValueAsString(persons);
                    System.out.println(jsonValue);
                    return jsonValue;
                }else {
                    System.out.println("There is no any records.");
                }

            }else {
                var person = personDAO.getPersonById(command.getId());
                if(person != null){
                    jsonValue = objectMapper.writeValueAsString(person);
                    System.out.println(jsonValue);
                    return jsonValue;
                }else {
                    System.out.println("No person found with id: " + command.getId());
                }
            }
        }catch (JsonProcessingException e){
            System.out.println(e.getMessage());
        }
        return jsonValue;
    }

    private void createPerson(Command command) {
        var id = personDAO.createPerson(command.getPerson());
        if(id != -1){
            System.out.printf("The person record has been created with id =  %d", id);
        }else {
            System.out.println("There occurred an error. Please, try again.");
        }
    }
}
