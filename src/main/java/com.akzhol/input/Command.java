package com.akzhol.input;

import com.akzhol.enums.CommandType;
import com.akzhol.model.Person;

public class Command {
    private CommandType commandType;
    private Integer id;
    private Person person;

    public Command() {

    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


    public CommandType getCommandType() {
        return commandType;
    }

    public Integer getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

}
