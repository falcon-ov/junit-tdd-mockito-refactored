package validation;

import org.example.model.Person;
import org.example.validation.PersonValidator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonValidatorTest {

    private final PersonValidator validator = new PersonValidator();

    @Test
    void validate_shouldReturnError_whenPersonIsNull() {
        List<String> errors = validator.validate(null);
        assertEquals(1, errors.size());
        assertEquals("Person is null", errors.get(0));
    }

    @Test
    void validate_shouldReturnErrors_whenFieldsAreInvalid() {
        Person person = new Person("", "", "invalidemail");
        List<String> errors = validator.validate(person);

        assertEquals(3, errors.size());
        assertTrue(errors.contains("Name must not be empty"));
        assertTrue(errors.contains("Surname must not be empty"));
        assertTrue(errors.contains("Email must contain '@'"));
    }

    @Test
    void validate_shouldReturnNoErrors_whenPersonIsValid() {
        Person person = new Person("John", "Doe", "john.doe@example.com");
        List<String> errors = validator.validate(person);

        assertTrue(errors.isEmpty());
    }

    @Test
    void validate_shouldReturnError_whenEmailIsNull() {
        Person person = new Person("John", "Doe", null);
        List<String> errors = validator.validate(person);

        assertEquals(1, errors.size());
        assertEquals("Email must contain '@'", errors.get(0));
    }
}
