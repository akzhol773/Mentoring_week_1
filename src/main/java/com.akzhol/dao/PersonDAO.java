package com.akzhol.dao;

import com.akzhol.exception.DatabaseAccessException;
import com.akzhol.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    public Person getPersonById(int id) {
        String sql = "SELECT id, name, age FROM person WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Person person = new Person();
                    person.setId(resultSet.getLong("id"));
                    person.setName(resultSet.getString("name"));
                    person.setAge(resultSet.getInt("age"));
                    return person;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException("Error fetching a person from the database. Please, try again.", e);
        }
        return null;
    }


    public int createPerson(Person person) {
        String sql = "INSERT INTO person (name, age) VALUES (?, ?) RETURNING id";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException("Error create a person. Please, try again.", e);
        }
        return -1;
    }


    public void updatePerson(Person person, int id) {
        String sql = "UPDATE person SET name = ?, age = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseAccessException("Error updating a person. Please, try again.", e);
        }
    }


    public void deletePersonById(int id) {
        String sql = "DELETE FROM person WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseAccessException("Error deleting a person from the database", e);
        }
    }


    public List<Person> getAllPerson() {
        String sql = "SELECT id, name, age FROM person";
        List<Person> persons = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getLong("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                persons.add(person);
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException("Error fetching persons from the database", e);
        }
        return persons;
    }

}
