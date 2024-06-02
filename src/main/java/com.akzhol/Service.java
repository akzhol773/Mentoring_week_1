package com.akzhol;

public class Service {

    private final Storage storage;

    public Service(Storage storage) {
        this.storage = storage;
    }

    public void execute(Command command){
        switch (command.getCommandType()){
            case CREATE:
                storage.create(command.getValue());
                break;
            case GET:
                if(command.getId()==null){
                    storage.getAllData();
                }else {
                    storage.getById(command.getId());
                }
                break;
            case UPDATE:
                storage.updateData(command.getId(), command.getValue());
                break;
            case DELETE:
                storage.deleteData(command.getId());
                break;
            default:
                throw new InvalidCommandException("Invalid command");

        }
    }


}
