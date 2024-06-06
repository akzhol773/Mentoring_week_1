package com.akzhol;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Validator {

    public void validate(String userInput){

        if(userInput == null || userInput.isEmpty()){
            throw new InvalidCommandException("Invalid command: " + userInput);
        }

        String[] parts = userInput.split(" ");

        switch (parts[0].toUpperCase()){
            case "GET":
                validateGet(parts);
                break;
            case "CREATE":
                validateCreate(parts);
                break;
            case "UPDATE":
                validateUpdate(parts);
                break;
            case "DELETE":
                validateDelete(parts);
                break;
            default:
                throwIllegalCommandException("Invalid command.");

        }


    }

    public void validateDelete(String[] parts) {
        if (parts == null || parts.length != 2) {
            throwIllegalCommandException("Command parts are missing or incorrect.");
        }

        if (!"DELETE".equalsIgnoreCase(parts[0])) {
            throwIllegalCommandException("Command must start with DELETE.");
        }

        if (isNotInt(parts[1])) {
           throwIllegalCommandException("The second part must be a valid integer.");
        }
    }


    public void validateUpdate(String[] parts) {
        if (parts == null || parts.length != 4) {
            throwIllegalCommandException("Command parts are missing or incomplete.");
        }

        if (!"UPDATE".equalsIgnoreCase(parts[0])) {
           throwIllegalCommandException("Command must start with UPDATE.");
        }

        if (isNotInt(parts[1]) || isNotInt(parts[3])) {
            throwIllegalCommandException("The second and fourth parts must be a valid integer.");
        }

        if (isValidJsonString(parts[2], Integer.parseInt(parts[3]))) {
            throwIllegalCommandException("Invalid command");
        }
    }


    public void validateCreate(String[] parts) {
        if (isNotInt(parts[2])) {
            throwIllegalCommandException("The third part must be a valid integer.");
        }

        if (parts == null || parts.length != 3) {
            throwIllegalCommandException("Command parts are missing or incorrect.");
        }

        if (!"CREATE".equalsIgnoreCase(parts[0])) {
            throwIllegalCommandException("Command must start with CREATE.");
        }

        if (isValidJsonString(parts[1], Integer.parseInt(parts[2]))) {
            throwIllegalCommandException("Invalid command.");
        }




    }

    private void validateGet(String[] parts){
        if (parts.length > 2) {
            throwIllegalCommandException("Invalid command.");
        }

        if (!"GET".equalsIgnoreCase(parts[0])) {
            throwIllegalCommandException("Command must start with GET.");
        }

        if (parts.length > 1 && isNotInt(parts[1])) {
            throwIllegalCommandException("Invalid command");
        }

    }

    private void throwIllegalCommandException(String message) {
        throw new InvalidCommandException(message);
    }

    private boolean isNotInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    private boolean isValidJsonString(String name, Integer age) {
        String jsonString = String.format("{\"name\": \"%s\", \"age\": %d}", name, age);
        try {
            new ObjectMapper().readValue(jsonString, Person.class);
        } catch (IOException e) {
            return true;
        }
        return false;
    }
}
