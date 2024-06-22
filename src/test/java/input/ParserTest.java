package input;

import com.akzhol.enums.CommandType;
import com.akzhol.exception.InvalidCommandException;
import com.akzhol.input.Parser;
import com.akzhol.input.Validator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    ObjectMapper objectMapper = new ObjectMapper();
    Validator validator = new Validator(objectMapper);
    Parser parser = new Parser(validator, objectMapper);

    @Test
    public void parseCreateTest(){

//        given
        var input = "create {\"name\": \"Ali\", \"age\" : 57}";

//        when
        var result = parser.parse(input);

//        then
        assertEquals(CommandType.CREATE, result.getCommandType());
        assertEquals(null, result.getId());
        assertNull(result.getPerson().getId());
        assertEquals("Ali", result.getPerson().getName());
        assertEquals(57, result.getPerson().getAge());
    }


    @Test
    public void parseGetTest(){
//        given
        var input = "get 1";
//        when
        var result = parser.parse(input);

//        then

        assertAll("result",
                ()-> assertEquals(CommandType.GET, result.getCommandType(), "Command type should be Get"),
                ()-> assertEquals(1, result.getId(), "Id should be 1"),
                ()-> assertNull(result.getPerson(), "Person object should be null")
                );
    }


    @Test
    public void parseUpdateTest(){
//        given
        var input = "update 2 {\"name\": \"John\", \"age\" : 35}";

//        when
        var result = parser.parse(input);

//        then

        assertAll("result",
                ()-> assertEquals(CommandType.UPDATE, result.getCommandType(), "Command type should be Update"),
                ()-> assertEquals(2, result.getId(), "Id should be 2"),
                ()-> assertNotNull(result.getPerson(), "Person object should not be null"),
                ()-> assertNull(result.getPerson().getId(), "Id should be null"),
                ()-> assertEquals("John", result.getPerson().getName(), "Person name should be John"),
                ()-> assertEquals(35, result.getPerson().getAge(), "Person name should be 35")
        );
    }


    @Test
    public void parseDeleteTest(){
//        given
        var input = "delete 1";
//        when
        var result = parser.parse(input);

//        then

        assertAll("result",
                ()-> assertEquals(CommandType.DELETE, result.getCommandType(), "Command type should be Delete"),
                ()-> assertEquals(1, result.getId(), "Id should be 1"),
                ()-> assertNull(result.getPerson(), "Person object should be null")
        );
    }


    @Test
    public void parseShouldThrowExceptionWhenInputIsNull() {
        //given
        String input = null;

        //when
        Exception ex = assertThrows(
                InvalidCommandException.class,
                () -> parser.parse(input)
        );

        //then
        assertEquals("Invalid command: null", ex.getMessage());
    }

    @Test
    public void parseShouldThrowExceptionWhenInputIsWrong() {
        //given
        String input = "cree {}";

        //when
        Exception ex = assertThrows(
                InvalidCommandException.class,
                () -> parser.parse(input)
        );

        //then
        assertEquals("Invalid command.", ex.getMessage());
    }

    @Test
    public void parseShouldThrowExceptionWhenJSONIsWrong() {
        //given
        String input = "update 2 {name\": \"John\", \"age\" : 35}";

        //when
        Exception ex = assertThrows(
                InvalidCommandException.class,
                () -> parser.parse(input)
        );

        //then
        assertEquals("Invalid JSON data for Person object creation.", ex.getMessage());
    }









}

























