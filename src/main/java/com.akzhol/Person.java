package com.akzhol;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Person {

    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Person person = new Person("bob", 25);

            String jsonString = objectMapper.writeValueAsString(person);
            System.out.println("JSON String: " + jsonString);

            Person deserializedPerson = objectMapper.readValue(jsonString, Person.class);
            System.out.println("Deserialized Person: " + deserializedPerson);
            System.out.println(person.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
