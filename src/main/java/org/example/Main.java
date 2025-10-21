package org.example;

import org.example.dao.SkillSetDAO;
import org.example.db.DatabaseConnection;
import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.example.repository.SkillSetRepository;
import org.example.repository.impl.DatabaseSkillSetRepository;
import org.example.repository.impl.InMemorySkillSetRepository;
import org.example.service.SkillSetComparator;
import org.example.service.SkillSetService;
import org.example.service.impl.SkillSetServiceImpl;
import org.example.validation.SkillSetValidator;
import org.example.validation.Validator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Validator<SkillSet> validator = new SkillSetValidator();
        SkillSetRepository repository = new InMemorySkillSetRepository();
        SkillSetService service = new SkillSetServiceImpl(repository, validator);

        service.importFromCsv();

        System.out.println("Before sorting:");
        List<SkillSet> list = repository.getAll();
        for (SkillSet s : list) {
            System.out.println(s.getPerson().getName() + " -> " + service.getRating(s));
        }

        Collections.sort(list, new SkillSetComparator(service));

        System.out.println("\nAfter sorting:");
        for (SkillSet s : list) {
            System.out.println(s.getPerson().getName() + " -> " + service.getRating(s));
        }
    }
}