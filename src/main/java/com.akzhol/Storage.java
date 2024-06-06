package com.akzhol;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private final Parser parser;
    public Storage(Parser parser) {
        this.parser = parser;
    }
    private HashMap<Integer, Person> data = new HashMap<>();

    public void create(Person person){
        Integer id = data.size()+1;
        data.put(id, person);
        System.out.println("Person saved with id = " + id);
    }

    public boolean checkPresence(int id) {
        return !data.containsKey(id);
    }


    public String getAllData() {
       return parser.parseAllData(data);
    }

    public String getById(int id) {
        if(checkPresence(id)){
            throw new IdNotFoundException("Given ID not found");
        }
        System.out.println(parser.getJsonString(data.get(id)));
        return parser.getJsonString(data.get(id));
    }


    public void updateData(int id, Person person) {
        if(checkPresence(id)){
            throw new IdNotFoundException("Given ID not found");
        }
        data.put(id, person);
        System.out.println("String with id = " + id + " updated");
    }

    public void deleteData(int id) {
        if(checkPresence(id)){
            throw new IdNotFoundException("Given ID not found");
        }
        data.remove(id);
        System.out.println("String with id = " + id + " deleted");
    }

}
