package com.akzhol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Service {

    private final Storage storage;
    private final ObjectMapper objectMapper;

    public Service(Storage storage, ObjectMapper objectMapper) {
        this.storage = storage;
        this.objectMapper = objectMapper;
    }

    public void execute(Command command){
        switch (command.getCommandType()){
            case CREATE:
                storage.create(command.getPerson());
                System.out.println("Person created with ID: " + storage.getPersonId(command.getPerson()));
                break;
            case GET:
                try{
                if(command.getId()==null){
                    System.out.println(objectMapper.writeValueAsString(storage.getAllData()));
                }else {
                    System.out.println(objectMapper.writeValueAsString(storage.getById(command.getId())));
                }
                }catch (JsonProcessingException e){
                    System.out.println(e.getMessage());
                }
                break;
            case UPDATE:
                storage.updateData(command.getId(), command.getPerson());
                System.out.printf("Person with id = %d updated", command.getId());
                break;
            case DELETE:
                storage.deleteData(command.getId());
                System.out.printf("Person with id = %d deleted", command.getId());
                break;
            default:
                throw new InvalidCommandException("Invalid command");

        }
    }


}
