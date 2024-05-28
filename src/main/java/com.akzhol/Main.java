package com.akzhol;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    private static HashMap<Integer, String> data = new HashMap<>();

    public static void main(String[] args) throws IOException {

        Properties properties = new Properties();
        InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties");
        properties.load(input);

        String filePath = properties.getProperty("file.path");

        Scanner scanner = new Scanner(System.in);

        while(true){

            System.out.println("-------------------------------------------------------------------------");
            System.out.println("| Program usage:                                                         |");
            System.out.println("| Command to create a string: CREATE {some_string}                       |");
            System.out.println("| Command to get a string: GET or GET {id}                               |");
            System.out.println("| Command to update a string: UPDATE {id} {new_string_value}             |");
            System.out.println("| Command to delete a string: DELETE {id}                                |");
            System.out.println("| Enter 'Exit' to stop the program.                                      |");
            System.out.println("-------------------------------------------------------------------------");


            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("Exit")){
                saveToFile(filePath);
                break;
            }

            String[] parts = userInput.split(" ", 2);
            String command = parts[0];

            switch (command.toUpperCase()){
                case "CREATE":
                    System.out.println(handleCreate(parts));
                    break;
                case "GET":
                    System.out.println(handleGet(parts));
                    break;
                case "UPDATE":
                    System.out.println(handleUpdate(parts));
                    break;
                case "DELETE":
                    System.out.println(handleDelete(parts));
                    break;
                default:
                    System.out.println("Please, enter correct command.");;

            }
        }

    }

    private static void saveToFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<Integer, String> entry : data.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        }
    }

    private static String handleDelete(String[] parts) {
        int id = Integer.parseInt(parts[1]);
        if (parts.length != 2) {
            return "Invalid DELETE command. Usage: DELETE {id}";
        } else if (!data.containsKey(id)) {
                return "String with id = " + id + " not found";
        }
        data.remove(id);
        return "String with id = " + id + " deleted";
    }

    private static String handleUpdate(String[] parts) {
        if (parts.length < 3) {
            return "Invalid UPDATE command. Usage: UPDATE {id} {new_string_value}";

        }
        int id = Integer.parseInt(parts[1]);
        String newValue = parts[2];
        if (!data.containsKey(id)) {
            return "String with id = " + id + " not found";
        }
        data.put(id, newValue);
        return "String with id = " + id + " updated";
    }


    private static String handleGet(String[] parts) {
        if (parts.length == 1) {

            return data.toString();
        } else if (parts.length == 2) {
            int id = Integer.parseInt(parts[1]);
            return data.get(id);
        } else {
            return "Invalid GET command. Usage: GET or GET {id}";
        }
    }

    private static String handleCreate(String[] parts) {
        if (parts.length < 2) {
            return "Invalid CREATE command. Usage: CREATE {some_string}";
        }
        String value = parts[1];
        Integer id = data.size()+1;
        data.put(id, value);
        return "String saved with id = " + id;
    }
}
