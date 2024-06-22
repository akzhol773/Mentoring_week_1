package com.akzhol.input;

import com.akzhol.exception.InvalidCommandException;
import com.akzhol.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;


public class Validator {
   private final ObjectMapper objectMapper;
    public Validator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void validate(String userInput) {

        if (userInput == null || userInput.isEmpty()) {
            throw new InvalidCommandException("Invalid command: " + userInput);
        }

        String[] parts = userInput.split(" ");

        switch (parts[0].toUpperCase()) {
            case "GET":
                validateGet(parts);
                break;
            case "CREATE":
                validateCreate(userInput);
                break;
            case "UPDATE":
                validateUpdate(userInput);
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


    public void validateUpdate(String userInput) {

        String[] parts = userInput.split(" ");

        if (!"UPDATE".equalsIgnoreCase(parts[0])) {
            throwIllegalCommandException("Command must start with UPDATE.");
        }

        if (isNotInt(parts[1])) {
            throwIllegalCommandException("The second part must be a valid integer.");
        }

        String jsonString = getJsonValue(userInput);
        try {
            new JSONObject(jsonString);
            objectMapper.readValue(jsonString, Person.class);
        } catch (JSONException | JsonProcessingException e) {
            throwIllegalCommandException("Invalid JSON data for Person object creation.");
        }
    }


    public void validateCreate(String userInput) {

        String[] parts = userInput.split(" ");

        if (!"CREATE".equalsIgnoreCase(parts[0])) {
            throwIllegalCommandException("Command must start with CREATE.");
        }

        String jsonString = getJsonValue(userInput);

        try {
            new JSONObject(jsonString);
            objectMapper.readValue(jsonString, Person.class);
        } catch (JSONException | JsonProcessingException e) {
            throwIllegalCommandException("Invalid JSON data for Person object creation.");
        }
    }

    public void validateGet(String[] parts) {
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


    private String getJsonValue(String userInput) {
        int jsonStartIndex = userInput.indexOf("{");
        if (jsonStartIndex == -1) {
            throwIllegalCommandException("Invalid JSON.");
        }

        int jsonEndIndex = userInput.lastIndexOf("}");
        if (jsonEndIndex == -1) {
            throwIllegalCommandException("Invalid JSON.");
        }

        return userInput.substring(jsonStartIndex, jsonEndIndex + 1);
    }


}
