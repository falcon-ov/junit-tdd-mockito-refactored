package org.example.io;

import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.example.validation.SkillSetValidator;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CsvSkillSetReader implements Reader<SkillSet> {

    private final SkillSetValidator validator;
    private final boolean skipHeader;

    public CsvSkillSetReader() {
        this(new SkillSetValidator(), true);
    }

    public CsvSkillSetReader(SkillSetValidator validator, boolean skipHeader) {
        this.validator = validator;
        this.skipHeader = skipHeader;
    }

    @Override
    public List<SkillSet> readAll(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            return parseLines(lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV: " + filePath, e);
        }
    }

    private List<SkillSet> parseLines(List<String> lines) {
        List<SkillSet> result = new ArrayList<>();
        int start = skipHeader ? 1 : 0;

        for (int i = start; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            SkillSet skillSet = parseLine(line, i + 1);
            result.add(skillSet);
        }
        return result;
    }

    private SkillSet parseLine(String line, int lineNum) {
        String[] parts = line.split(",", -1);

        if (parts.length != 7) {
            throw new IllegalArgumentException(
                    "Line " + lineNum + ": expected 7 columns, got " + parts.length
            );
        }

        try {
            Person person = new Person(
                    parts[0].trim(),
                    parts[1].trim(),
                    parts[2].trim()
            );
            Skill skill = new Skill(
                    parts[3].trim(),
                    parts[4].trim()
            );
            int level = Integer.parseInt(parts[5].trim());
            int years = Integer.parseInt(parts[6].trim());

            SkillSet skillSet = new SkillSet(person, skill, level, years);

            // Валидация
            List<String> errors = validator.validate(skillSet);
            if (!errors.isEmpty()) {
                throw new IllegalArgumentException(
                        "Line " + lineNum + ": " + String.join("; ", errors)
                );
            }

            return skillSet;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Line " + lineNum + ": invalid number format", e
            );
        }
    }
}