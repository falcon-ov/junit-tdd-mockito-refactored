package org.example;

import org.example.dao.SkillSetDAO;
import org.example.db.DatabaseConnection;
import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.example.repository.SkillSetRepository;
import org.example.repository.impl.DatabaseSkillSetRepository;
import org.example.repository.impl.InMemorySkillSetRepository;
import org.example.service.SkillSetService;
import org.example.service.impl.SkillSetServiceImpl;
import org.example.validation.SkillSetValidator;
import org.example.validation.Validator;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
//        Connection connection = DatabaseConnection.getConnection();
//        SkillSetDAO dao = new SkillSetDAO(connection);
//        SkillSetRepository repository = new DatabaseSkillSetRepository(dao);
//        Validator<SkillSet> validator = new SkillSetValidator();
//        SkillSetRepository repository = new InMemorySkillSetRepository();
//        SkillSetService service = new SkillSetServiceImpl(repository, validator);
//
//        Person person = new Person("Name","Surname","email@gmail.com");
//        service.addSkillSet(new SkillSet(person, new Skill("Java", "IT"), 10, 5));
//
//        for(SkillSet skillSet : service.getAllSkills(person)){
//            System.out.println("-".repeat(40));
//            System.out.println(skillSet.getPerson().getName());
//            System.out.println(skillSet.getPerson().getSurname());
//            System.out.println(skillSet.getPerson().getEmail());
//            System.out.println(skillSet.getSkill().getName());
//            System.out.println(skillSet.getSkill().getDomain());
//            System.out.println(skillSet.getLevel());
//            System.out.println(skillSet.getYearsExperience());
//            System.out.println("-".repeat(40));
//        }
    }
}