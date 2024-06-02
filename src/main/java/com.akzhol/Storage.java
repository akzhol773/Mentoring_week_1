package com.akzhol;

import java.util.HashMap;

public class Storage {
    private HashMap<Integer, String> data = new HashMap<>();

    public void create(String value){
        Integer id = data.size()+1;
        data.put(id, value);
        System.out.println("String saved with id = " + id);
    }

    public boolean checkPresence(int id) {
        return data.containsKey(id);
    }


    public String getAllData() {
        System.out.println(data.toString());
        return data.toString();
    }

    public String getById(int id) {
        if(!checkPresence(id)){
            throwIdNotFoundException();
        }
        System.out.println(data.get(id));
        return data.get(id);
    }


    public void updateData(int id, String newValue) {
        if(!checkPresence(id)){
            throwIdNotFoundException();
        }
        data.put(id, newValue);
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
}
