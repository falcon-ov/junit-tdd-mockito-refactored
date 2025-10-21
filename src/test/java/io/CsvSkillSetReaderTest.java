package io;

import org.example.io.CsvSkillSetReader;
import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvSkillSetReaderTest {

    @Test
    void readAll_shouldReturnSkillSetsFromCsv() {
        CsvSkillSetReader reader = new CsvSkillSetReader();
        List<SkillSet> skillSets = reader.readAll();

        assertNotNull(skillSets);
        assertFalse(skillSets.isEmpty(), "CSV should not be empty");

        SkillSet first = skillSets.get(0);
        assertNotNull(first.getPerson());
        assertNotNull(first.getSkill());
        assertTrue(first.getLevel() >= 0);
        assertTrue(first.getYearsExperience() >= 0);
    }

    @Test
    void parseLine_shouldThrowExceptionForInvalidColumns() {
        CsvSkillSetReader reader = new CsvSkillSetReader();
        String invalidLine = "Ivan;Ivanov;ivan@example.com;Java;Backend;5"; // только 6 колонок

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reader.parseLine(invalidLine, 1);
        });

        assertTrue(exception.getMessage().contains("expected 7 columns"));
    }

    @Test
    void parseLine_shouldThrowExceptionForInvalidNumber() {
        CsvSkillSetReader reader = new CsvSkillSetReader();
        String invalidLine = "Ivan;Ivanov;ivan@example.com;Java;Backend;five;2"; // level не число

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reader.parseLine(invalidLine, 1);
        });

        assertTrue(exception.getMessage().contains("invalid number format"));
    }

    @Test
    void parseLine_shouldParseCorrectLine() {
        CsvSkillSetReader reader = new CsvSkillSetReader();
        String validLine = "Ivan;Ivanov;ivan@example.com;Java;Backend;5;2";

        SkillSet skillSet = reader.parseLine(validLine, 1);

        assertEquals("Ivan", skillSet.getPerson().getName());
        assertEquals("Ivanov", skillSet.getPerson().getSurname());
        assertEquals("ivan@example.com", skillSet.getPerson().getEmail());
        assertEquals("Java", skillSet.getSkill().getName());
        assertEquals("Backend", skillSet.getSkill().getDomain());
        assertEquals(5, skillSet.getLevel());
        assertEquals(2, skillSet.getYearsExperience());
    }
}
