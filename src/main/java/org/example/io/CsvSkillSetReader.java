package org.example.io;

import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.example.validation.SkillSetValidator;

import java.io.InputStream;
import java.util.*;

public class CsvSkillSetReader implements Reader<SkillSet> {

    private static final String DELIMITER = ";";
    private static final String DEFAULT_FILE_PATH = "data/skillset.csv";

    private final SkillSetValidator validator;
    private final boolean skipHeader;

    public CsvSkillSetReader() {
        this.validator = new SkillSetValidator();
        this.skipHeader = true;
    }

    @Override
    public List<SkillSet> readAll() {
        try {
            // читаем CSV из resources по константе
            InputStream is = getClass().getClassLoader().getResourceAsStream(DEFAULT_FILE_PATH);
            if (is == null) {
                throw new RuntimeException("CSV file not found in resources: " + DEFAULT_FILE_PATH);
            }

            List<String> lines = new ArrayList<>();
            try (Scanner scanner = new Scanner(is)) {
                while (scanner.hasNextLine()) {
                    lines.add(scanner.nextLine());
                }
            }

            List<SkillSet> result = new ArrayList<>();
            int start = skipHeader ? 1 : 0;

            for (int i = start; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) continue;
                result.add(parseLine(line, i + 1));
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV from resources: " + DEFAULT_FILE_PATH, e);
        }
    }

    public SkillSet parseLine(String line, int lineNum) {
        String[] parts = line.split(DELIMITER, -1);

        if (parts.length != 7) {
            throw new IllegalArgumentException(
                    "Line " + lineNum + ": expected 7 columns, got " + parts.length
            );
        }

        try {
            Person person = new Person(parts[0].trim(), parts[1].trim(), parts[2].trim());
            Skill skill = new Skill(parts[3].trim(), parts[4].trim());
            int level = Integer.parseInt(parts[5].trim());
            int years = Integer.parseInt(parts[6].trim());

            SkillSet skillSet = new SkillSet(person, skill, level, years);

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
