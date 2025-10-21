package io;

import org.example.io.CsvSkillSetReader;
import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.example.validation.SkillSetValidator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvSkillSetReaderTest {

    @Test
    public void testReadValidCsv() throws IOException {
        // создаем временный CSV-файл
        Path tempFile = Files.createTempFile("skills", ".csv");
        Files.write(tempFile, List.of(
                "firstName,lastName,email,skillName,skillCategory,level,years",
                "Ivan,Ivanov,ivan@test.com,Java,Programming,5,3"
        ));

        CsvSkillSetReader reader = new CsvSkillSetReader(new SkillSetValidator(), true);
        List<SkillSet> result = reader.readAll(tempFile.toString());

        assertEquals(1, result.size());
        SkillSet skillSet = result.get(0);

        assertEquals("Ivan", skillSet.getPerson().getName());
        assertEquals("Ivanov", skillSet.getPerson().getSurname());
        assertEquals("ivan@test.com", skillSet.getPerson().getEmail());
        assertEquals("Java", skillSet.getSkill().getName());
        assertEquals("Programming", skillSet.getSkill().getDomain());
        assertEquals(5, skillSet.getLevel());
        assertEquals(3, skillSet.getYearsExperience());
    }

    @Test
    public void testInvalidNumberFormat() throws IOException {
        Path tempFile = Files.createTempFile("skills_invalid_number", ".csv");
        Files.write(tempFile, List.of(
                "firstName,lastName,email,skillName,skillCategory,level,years",
                "Ivan,Ivanov,ivan@test.com,Java,Programming,abc,3"
        ));

        CsvSkillSetReader reader = new CsvSkillSetReader(new SkillSetValidator(), true);

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                reader.readAll(tempFile.toString())
        );

        assertTrue(ex.getMessage().contains("invalid number format"));
    }

    @Test
    public void testWrongColumnCount() throws IOException {
        Path tempFile = Files.createTempFile("skills_wrong_columns", ".csv");
        Files.write(tempFile, List.of(
                "firstName,lastName,email,skillName,skillCategory,level,years",
                "Ivan,Ivanov,ivan@test.com,Java,Programming,5" // только 6 колонок
        ));

        CsvSkillSetReader reader = new CsvSkillSetReader(new SkillSetValidator(), true);

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                reader.readAll(tempFile.toString())
        );

        assertTrue(ex.getMessage().contains("expected 7 columns"));
    }

    @Test
    public void testEmptyLinesSkipped() throws IOException {
        Path tempFile = Files.createTempFile("skills_empty_lines", ".csv");
        Files.write(tempFile, List.of(
                "firstName,lastName,email,skillName,skillCategory,level,years",
                "",
                "Ivan,Ivanov,ivan@test.com,Java,Programming,5,3",
                "   "
        ));

        CsvSkillSetReader reader = new CsvSkillSetReader(new SkillSetValidator(), true);
        List<SkillSet> result = reader.readAll(tempFile.toString());

        assertEquals(1, result.size());
    }
}
