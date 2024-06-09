package com.akzhol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Parser {

    private final Validator validator;
    private final ObjectMapper objectMapper;

    public Parser(Validator validator, ObjectMapper objectMapper) {
        this.validator = validator;
        this.objectMapper = objectMapper;
    }

    public Command parse(String userInput){

        validator.validate(userInput);

        String[] parts = userInput.split(" ");

        String command = parts[0];

        Command.CommandType actions = Command.CommandType.valueOf(command.toUpperCase());

        Command commandObject = new Command();

        switch (actions){

            case CREATE:
                commandObject.setCommandType(Command.CommandType.CREATE);
                commandObject.setPerson(parseJson(getJsonValue(userInput)));
                break;

            case GET:
               if(parts.length == 1){
                   commandObject.setCommandType(Command.CommandType.GET);

               }else {
                   commandObject.setCommandType(Command.CommandType.GET);
                   commandObject.setId(Integer.parseInt(parts[1]));
               }
               break;
            case UPDATE:
                commandObject.setCommandType(Command.CommandType.UPDATE);
                commandObject.setId(Integer.parseInt(parts[1]));
                commandObject.setPerson(parseJson(getJsonValue(userInput)));
                break;
            case DELETE:
                commandObject.setCommandType(Command.CommandType.DELETE);
                commandObject.setId(Integer.parseInt(parts[1]));
                break;
            default:
               throw new InvalidCommandException("Invalid command");
        }
      return commandObject;
    }
    private Person parseJson(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, Person.class);
        } catch (JsonProcessingException e) {
            throw new InvalidJsonException(e.getMessage());
        }
    }
    public String getJsonValue(String userInput) {
        int jsonStartIndex = userInput.indexOf("{");
        return userInput.substring(jsonStartIndex);
    }
}
