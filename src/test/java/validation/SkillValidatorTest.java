package validation;

import org.example.model.Skill;
import org.example.validation.SkillValidator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkillValidatorTest {

    private final SkillValidator validator = new SkillValidator();

    @Test
    void validate_shouldReturnError_whenSkillIsNull() {
        List<String> errors = validator.validate(null);
        assertEquals(1, errors.size());
        assertEquals("Skill is null", errors.get(0));
    }

    @Test
    void validate_shouldReturnErrors_whenNameOrDomainEmpty() {
        Skill skill = new Skill("", "");
        List<String> errors = validator.validate(skill);

        assertEquals(2, errors.size());
        assertTrue(errors.contains("Skill name must not be empty"));
        assertTrue(errors.contains("Skill domain must not be empty"));
    }

    @Test
    void validate_shouldReturnNoErrors_whenSkillIsValid() {
        Skill skill = new Skill("Java", "Programming");
        List<String> errors = validator.validate(skill);

        assertTrue(errors.isEmpty());
    }

    @Test
    void validate_shouldReturnError_whenNameIsNull() {
        Skill skill = new Skill(null, "Programming");
        List<String> errors = validator.validate(skill);

        assertEquals(1, errors.size());
        assertEquals("Skill name must not be empty", errors.get(0));
    }

    @Test
    void validate_shouldReturnError_whenDomainIsNull() {
        Skill skill = new Skill("Java", null);
        List<String> errors = validator.validate(skill);

        assertEquals(1, errors.size());
        assertEquals("Skill domain must not be empty", errors.get(0));
    }
}
