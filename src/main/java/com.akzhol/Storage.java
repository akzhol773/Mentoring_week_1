package com.akzhol;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Storage {
    private HashMap<Integer, Person> data = new HashMap<>();
    private final AtomicInteger idGenerator;

    public Storage(AtomicInteger idGenerator) {
        this.idGenerator = idGenerator;
    }

    public void create(Person person){
        Integer id = idGenerator.incrementAndGet();
        data.put(id, person);
    }

    public HashMap<Integer, Person> getAllData() {
       return data;
    }

    public Person getById(int id) {
        if(!data.containsKey(id)){
            throw new IdNotFoundException("Given ID not found");
        }
        return data.get(id);
    }

    public void updateData(int id, Person person) {
        if(!data.containsKey(id)){
            throw new IdNotFoundException("Given ID not found");
        }
        data.put(id, person);
    }

    public void deleteData(int id) {
        if(!data.containsKey(id)){
            throw new IdNotFoundException("Given ID not found");
        }
        data.remove(id);
    }

    public Integer getPersonId(Person person){
        for (Map.Entry<Integer, Person> entry : data.entrySet()) {
            if (entry.getValue().equals(person)) {
                return entry.getKey();
            }
        }
        return null;
    }

}
