package com.akzhol;

public class Parser {

    private final Validator validator;

    public Parser(Validator validator) {
        this.validator = validator;
    }


    Command.CommandType actions = null;


    public Command parse(String userInput){

        validator.validate(userInput);

        String[] parts = userInput.split(" ");

        String command = parts[0];

        actions = Command.CommandType.valueOf(command.toUpperCase());

        Command commandObject = new Command();

        switch (actions){

            case CREATE:
                commandObject.setCommandType(Command.CommandType.CREATE);
                commandObject.setValue(getValueFromArray(parts, 1, parts.length));
                break;

            case GET:
               if(parts.length == 1){
                   commandObject.setCommandType(Command.CommandType.GET);

               }else {
                   commandObject.setCommandType(Command.CommandType.GET);
                   commandObject.setId(Integer.parseInt(parts[1]));
               }
               break;
            case UPDATE:
                commandObject.setCommandType(Command.CommandType.UPDATE);
                commandObject.setId(Integer.parseInt(parts[1]));
                commandObject.setValue(getValueFromArray(parts, 2, parts.length));
                break;
            case DELETE:
                commandObject.setCommandType(Command.CommandType.DELETE);
                commandObject.setId(Integer.parseInt(parts[1]));
                break;
            default:
               throw new InvalidCommandException("Invalid command");
        }
      return commandObject;
    }

    private String getValueFromArray(String[] array, int start, int end) {
        validator.validateArray(array, start, end);
        StringBuilder result = new StringBuilder();
        for (int i = start; i < end; i++) {
            result.append(array[i]);
            if (i < end - 1) {
                result.append(" ");
            }
        }
        return result.toString();
    }


}
