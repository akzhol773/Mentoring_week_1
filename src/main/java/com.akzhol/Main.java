package com.akzhol;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Validator validator = new Validator();
        ObjectMapper objectMapper = new ObjectMapper();
        Parser parser = new Parser(validator, objectMapper);
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage(parser);
        Service service = new Service(storage);

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("| Program usage:                                                         |");
        System.out.println("| Command to create a string: CREATE {name, age}                         |");
        System.out.println("| Command to get a string: GET or GET {id}                               |");
        System.out.println("| Command to update a string: UPDATE {id} {name, age}                    |");
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
