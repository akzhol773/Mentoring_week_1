package com.akzhol;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private HashMap<Integer, Person> data = new HashMap<>();
    ObjectMapper objectMapper = new ObjectMapper();


    public void create(String value){
        Integer id = data.size()+1;
        Person person = parseStringValue(value);
        data.put(id, person);
        System.out.println("String saved with id = " + id);
    }

    public boolean checkPresence(int id) {
        return data.containsKey(id);
    }


    public String getAllData() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        for (Map.Entry<Integer, Person> entry : data.entrySet()) {
            Integer key = entry.getKey();
            Person person = entry.getValue();
            String jsonString = getJsonString(person);
            jsonBuilder.append("\"").append(key).append("\":").append(jsonString).append(",");
        }

        // Remove the last comma
        if (jsonBuilder.length() > 1) {
            jsonBuilder.setLength(jsonBuilder.length() - 1);
        }

        jsonBuilder.append("}");
        String result = jsonBuilder.toString();
        System.out.println(result);
        return result;
    }

    public String getById(int id) {
        if(!checkPresence(id)){
            throwIdNotFoundException();
        }
        System.out.println(getJsonString(data.get(id)));
        return getJsonString(data.get(id));
    }


    public void updateData(int id, String newValue) {
        if(!checkPresence(id)){
            throwIdNotFoundException();
        }
        Person person = parseStringValue(newValue);
        data.put(id, person);
        System.out.println("String with id = " + id + " updated");
    }

    public void deleteData(int id) {
        if(!checkPresence(id)){
            throwIdNotFoundException();
        }
        data.remove(id);
        System.out.println("String with id = " + id + " deleted");
    }

    private void throwIdNotFoundException(){
        throw new IdNotFoundException("Given ID not found");
    }

    private Person parseStringValue(String value) {
        String[] parts = value.split(" ");
        String name = parts[0];
        Integer age = Integer.parseInt(parts[1]);

        String jsonString = String.format("{\"name\": \"%s\", \"age\": %d}", name, age);

        try {
            return objectMapper.readValue(jsonString, Person.class);
        } catch (JsonProcessingException e) {
            throw new InvalidJsonException(e.getMessage());
        }
    }

    private String getJsonString(Person person){
        try{
            return objectMapper.writeValueAsString(person);
        }catch (JsonProcessingException e){
            throw new InvalidJsonException(e.getMessage());
        }
    }
}
