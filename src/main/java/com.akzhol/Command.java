package com.akzhol;

public class Command {

    enum CommandType{
        CREATE,
        GET,
        UPDATE,
        DELETE

    }

    private CommandType commandType;
    private Integer id;
    private String value;

    public Command() {

    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public CommandType getCommandType() {
        return commandType;
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }


}
