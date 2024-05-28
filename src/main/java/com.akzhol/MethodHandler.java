package com.akzhol;

public class MethodHandler {

    Storage storage = new Storage();


public String handleCommand(String userInput){
    String[] parts = userInput.split(" ", 2);
    String[] partsForUpdate = userInput.split(" ", 3);
    String command = parts[0];
    switch (command.toUpperCase()){
        case "CREATE":
            return handleCreate(parts);
        case "GET":
            return handleGet(parts);
        case "UPDATE":
            return handleUpdate(partsForUpdate);
        case "DELETE":
            return handleDelete(parts);
        default:
            return "Please, enter correct command.";
    }
}

    public String handleDelete(String[] parts) {
        int id = Integer.parseInt(parts[1]);
        if (parts.length != 2) {
            return "Invalid DELETE command. Usage: DELETE {id}";
        } else if (!storage.checkPresence(id)) {
            return "String with id = " + id + " not found";
        }
        return storage.deleteData(id);

    }

    public String handleUpdate(String[] parts) {
        if (parts.length < 3) {
            return "Invalid UPDATE command. Usage: UPDATE {id} {new_string_value}";
        }
        int id = Integer.parseInt(parts[1]);
        String newValue = parts[2];
        if (!storage.checkPresence(id)) {
            return "String with id = " + id + " not found";
        }
       return storage.updateData(id, newValue);
    }


    public String handleGet(String[] parts) {
        if (parts.length == 1) {
            return storage.getAllData();
        } else if (parts.length == 2) {
            int id = Integer.parseInt(parts[1]);
            return storage.getById(id);
        } else {
            return "Invalid GET command. Usage: GET or GET {id}";
        }
    }

    public String handleCreate(String[] parts) {
        if (parts.length < 2) {
            return "Invalid CREATE command. Usage: CREATE {some_string}";
        }
        String value = parts[1];
        return storage.create(value);
    }
}
