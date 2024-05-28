package com.akzhol;

import java.util.HashMap;

public class Storage {
    private HashMap<Integer, String> data = new HashMap<>();

    public String create(String value){
        Integer id = data.size()+1;
        data.put(id, value);
        return "String saved with id = " + id;
    }


    public String getAllData() {
        return data.toString();
    }

    public String getById(int id) {
        return data.get(id);
    }

    public boolean checkPresence(int id) {
        return data.containsKey(id);
    }

    public String updateData(int id, String newValue) {
        data.put(id, newValue);
        return "String with id = " + id + " updated";
    }

    public String deleteData(int id) {
        data.remove(id);
        return "String with id = " + id + " deleted";
    }
}
