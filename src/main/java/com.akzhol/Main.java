package com.akzhol;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {

        ObjectMapper objectMapper = new ObjectMapper();
        Validator validator = new Validator(objectMapper);
        Parser parser = new Parser(validator, objectMapper);
        Scanner scanner = new Scanner(System.in);
        AtomicInteger atomicInteger = new AtomicInteger();
        Storage storage = new Storage(atomicInteger);
        Service service = new Service(storage, objectMapper);

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("| Program usage:                                                         |");
        System.out.println("| Command to create a string: CREATE {some JSON value}                   |");
        System.out.println("| Command to get a string: GET or GET {id}                               |");
        System.out.println("| Command to update a string: UPDATE {id} {some JSON value}                    |");
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
