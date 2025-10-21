package validation;

import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.example.validation.SkillSetValidator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkillSetValidatorTest {

    private final SkillSetValidator validator = new SkillSetValidator();

    @Test
    void validate_shouldReturnError_whenSkillSetIsNull() {
        List<String> errors = validator.validate(null);
        assertEquals(1, errors.size());
        assertEquals("SkillSet is null", errors.get(0));
    }

    @Test
    void validate_shouldReturnError_whenLevelIsInvalid() {
        Person person = new Person("John", "Doe", "john.doe@example.com");
        Skill skill = new Skill("Java", "Programming");

        SkillSet skillSet = new SkillSet(person, skill, 0, 5);
        List<String> errors = validator.validate(skillSet);
        assertTrue(errors.contains("Level must be between 1 and 10"));

        skillSet = new SkillSet(person, skill, 11, 5);
        errors = validator.validate(skillSet);
        assertTrue(errors.contains("Level must be between 1 and 10"));
    }

    @Test
    void validate_shouldReturnError_whenYearsExperienceIsNegative() {
        Person person = new Person("John", "Doe", "john.doe@example.com");
        Skill skill = new Skill("Java", "Programming");

        SkillSet skillSet = new SkillSet(person, skill, 5, -1);
        List<String> errors = validator.validate(skillSet);
        assertTrue(errors.contains("Years of experience cannot be negative"));
    }

    @Test
    void validate_shouldReturnErrors_whenPersonOrSkillInvalid() {
        Person invalidPerson = new Person("", "", "invalidemail");
        Skill invalidSkill = new Skill("", "");
        SkillSet skillSet = new SkillSet(invalidPerson, invalidSkill, 5, 3);

        List<String> errors = validator.validate(skillSet);

        // проверяем ошибки по Person
        assertTrue(errors.contains("Name must not be empty"));
        assertTrue(errors.contains("Surname must not be empty"));
        assertTrue(errors.contains("Email must contain '@'"));

        // проверяем ошибки по Skill
        assertTrue(errors.contains("Skill name must not be empty"));
        assertTrue(errors.contains("Skill domain must not be empty"));
    }

    @Test
    void validate_shouldReturnNoErrors_whenSkillSetIsValid() {
        Person person = new Person("John", "Doe", "john.doe@example.com");
        Skill skill = new Skill("Java", "Programming");
        SkillSet skillSet = new SkillSet(person, skill, 5, 3);

        List<String> errors = validator.validate(skillSet);
        assertTrue(errors.isEmpty());
    }
}
