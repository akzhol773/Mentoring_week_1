package input;

import com.akzhol.exception.InvalidCommandException;
import com.akzhol.input.Validator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorTest {

    ObjectMapper objectMapper = new ObjectMapper();
    Validator validator = new Validator(objectMapper);

    @Test
    public void testValidateDelete_NullParts(){
        InvalidCommandException exception = assertThrows(
                InvalidCommandException.class,
                () -> validator.validateDelete(null)
        );
        assertEquals("Command parts are missing or incorrect.", exception.getMessage());
    }

    @Test
    public void testValidateDelete_IncorrectPartsLength() {
        InvalidCommandException exception = assertThrows(
                InvalidCommandException.class,
                () -> validator.validateDelete(new String[] {"DELETE"})
        );
        assertEquals("Command parts are missing or incorrect.", exception.getMessage());
    }

    @Test
    public void testValidateDelete_InvalidCommand() {
        InvalidCommandException exception = assertThrows(
                InvalidCommandException.class,
                () -> validator.validateDelete(new String[] {"REMOVE", "123"})
        );
        assertEquals("Command must start with DELETE.", exception.getMessage());
    }

    @Test
    void testValidateDelete_InvalidInteger() {
        InvalidCommandException exception = assertThrows(
                InvalidCommandException.class,
                () -> validator.validateDelete(new String[] {"DELETE", "abc"})
        );
        assertEquals("The second part must be a valid integer.", exception.getMessage());
    }

    @Test
    void testValidateDelete_ValidCommand() {
        assertDoesNotThrow(() -> validator.validateDelete(new String[] {"DELETE", "123"}));
    }


    @Test
    void testValidateUpdate_InvalidCommand() {
        InvalidCommandException exception = assertThrows(
                InvalidCommandException.class,
                () -> validator.validateUpdate("MODIFY 123 {\"name\":\"John\"}")
        );
        assertEquals("Command must start with UPDATE.", exception.getMessage());
    }


    @Test
    void testValidateUpdate_InvalidInteger() {
        InvalidCommandException exception = assertThrows(
                InvalidCommandException.class,
                () -> validator.validateUpdate("UPDATE abc {\"name\":\"John\"}")
        );
        assertEquals("The second part must be a valid integer.", exception.getMessage());
    }

    @Test
    void testValidateUpdate_InvalidJson() {
        String invalidJson = "UPDATE 123 {name:\"John\"}";

        InvalidCommandException exception = assertThrows(
                InvalidCommandException.class,
                () -> validator.validateUpdate(invalidJson)
        );
        assertEquals("Invalid JSON data for Person object creation.", exception.getMessage());
    }

    @Test
    void testValidateUpdate_ValidCommand() {
        String validJson = "UPDATE 123 {\"name\":\"John\"}";

        assertDoesNotThrow(() -> validator.validateUpdate(validJson));
    }



    @Test
    void testValidateCreate_InvalidCommand() {
        InvalidCommandException exception = assertThrows(
                InvalidCommandException.class,
                () -> validator.validateCreate("INSERT {\"name\":\"John\"}")
        );
        assertEquals("Command must start with CREATE.", exception.getMessage());
    }

    @Test
    void testValidateCreate_InvalidJson() {
        String invalidJson = "CREATE {name:\"John\"}";

        InvalidCommandException exception = assertThrows(
                InvalidCommandException.class,
                () -> validator.validateCreate(invalidJson)
        );
        assertEquals("Invalid JSON data for Person object creation.", exception.getMessage());
    }

    @Test
    void testValidateCreate_ValidCommand() {
        String validJson = "CREATE {\"name\":\"John\"}";

        assertDoesNotThrow(() -> validator.validateCreate(validJson));
    }



    @Test
    void testValidateGet_TooManyParts() {
        InvalidCommandException exception = assertThrows(
                InvalidCommandException.class,
                () -> validator.validateGet(new String[] {"GET", "123", "extra"})
        );
        assertEquals("Invalid command.", exception.getMessage());
    }

    @Test
    void testValidateGet_InvalidCommand() {
        InvalidCommandException exception = assertThrows(
                InvalidCommandException.class,
                () -> validator.validateGet(new String[] {"FETCH", "123"})
        );
        assertEquals("Command must start with GET.", exception.getMessage());
    }

    @Test
    void testValidateGet_InvalidInteger() {
        InvalidCommandException exception = assertThrows(
                InvalidCommandException.class,
                () -> validator.validateGet(new String[] {"GET", "abc"})
        );
        assertEquals("Invalid command", exception.getMessage());
    }

    @Test
    void testValidateGet_ValidCommandWithoutInteger() {
        assertDoesNotThrow(() -> validator.validateGet(new String[] {"GET"}));
    }

    @Test
    void testValidateGet_ValidCommandWithInteger() {
        assertDoesNotThrow(() -> validator.validateGet(new String[] {"GET", "123"}));
    }


}
