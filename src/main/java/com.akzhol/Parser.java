package com.akzhol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Parser {

    private final Validator validator;

    private final ObjectMapper objectMapper;

    public Parser(Validator validator, ObjectMapper objectMapper) {
        this.validator = validator;
        this.objectMapper = objectMapper;
    }


    Command.CommandType actions = null;


    public Command parse(String userInput){

        validator.validate(userInput);

        String[] parts = userInput.split(" ");

        String command = parts[0];

        actions = Command.CommandType.valueOf(command.toUpperCase());

        Command commandObject = new Command();

        switch (actions){

            case CREATE:
                commandObject.setCommandType(Command.CommandType.CREATE);
                commandObject.setPerson(parseStringValue(parts[1], Integer.parseInt(parts[2])));
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
                commandObject.setPerson(parseStringValue(parts[2], Integer.parseInt(parts[3])));
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

    private Person parseStringValue(String name, Integer age) {
        String commaRemovedName = name.replace(",", "");

        String jsonString = String.format("{\"name\": \"%s\", \"age\": %d}", commaRemovedName, age);

        try {
            return objectMapper.readValue(jsonString, Person.class);
        } catch (JsonProcessingException e) {
            throw new InvalidJsonException(e.getMessage());
        }
    }

    public String getJsonString(Person person){
        try{
            return objectMapper.writeValueAsString(person);
        }catch (JsonProcessingException e){
            throw new InvalidJsonException(e.getMessage());
        }
    }

    public String parseAllData(HashMap<Integer, Person> data) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        for (Map.Entry<Integer, Person> entry : data.entrySet()) {
            Integer key = entry.getKey();
            Person person = entry.getValue();
            String jsonString = getJsonString(person);
            jsonBuilder.append("\"").append(key).append("\":").append(jsonString).append(",");
        }
        if (jsonBuilder.length() > 1) {
            jsonBuilder.setLength(jsonBuilder.length() - 1);
        }

        jsonBuilder.append("}");
        String result = jsonBuilder.toString();
        System.out.println(result);
        return result;
    }


}
