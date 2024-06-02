package com.akzhol;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static HashMap<Integer, String> data = new HashMap<>();

    public static void main(String[] args) throws IOException {

        Validator validator = new Validator();
        Parser parser = new Parser(validator);
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage();
        Service service = new Service(storage);

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("| Program usage:                                                         |");
        System.out.println("| Command to create a string: CREATE {some_string}                       |");
        System.out.println("| Command to get a string: GET or GET {id}                               |");
        System.out.println("| Command to update a string: UPDATE {id} {new_string_value}             |");
        System.out.println("| Command to delete a string: DELETE {id}                                |");
        System.out.println("| Enter 'Exit' to stop the program.                                      |");
        System.out.println("-------------------------------------------------------------------------");

        while(true){

            try{
                String userInput = scanner.nextLine();
                if (userInput.equalsIgnoreCase("Exit")){
                    break;
                }
                Command command = parser.parse(userInput);
                service.execute(command);

            }catch (InvalidCommandException | IdNotFoundException exception){
                System.out.println(exception.getMessage());
            }



        }

    }

}
