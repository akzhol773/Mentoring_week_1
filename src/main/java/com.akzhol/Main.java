package com.akzhol;

import com.akzhol.dao.PersonDAO;
import com.akzhol.exception.DatabaseAccessException;
import com.akzhol.exception.IdNotFoundException;
import com.akzhol.exception.InvalidCommandException;
import com.akzhol.exception.InvalidJsonException;
import com.akzhol.model.Command;
import com.akzhol.input.Parser;
import com.akzhol.input.Validator;
import com.akzhol.service.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ObjectMapper objectMapper = new ObjectMapper();
        Validator validator = new Validator(objectMapper);
        Parser parser = new Parser(validator, objectMapper);
        Scanner scanner = new Scanner(System.in);
        PersonDAO personDAO = new PersonDAO();
        Service service = new Service(objectMapper, personDAO);

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("| Program usage:                                                         |");
        System.out.println("| Command to create a string: CREATE {some JSON value}                   |");
        System.out.println("| Command to get a string: GET or GET {id}                               |");
        System.out.println("| Command to update a string: UPDATE {id} {some JSON value}              |");
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

            }catch (InvalidCommandException | IdNotFoundException | InvalidJsonException | DatabaseAccessException exception){
                System.out.println(exception.getMessage());
            }
        }

    }

}
