package org.example.repository.impl;

import org.example.dao.SkillSetDAO;
import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.example.repository.SkillSetRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseSkillSetRepository implements SkillSetRepository {

    private final SkillSetDAO dao;

    public DatabaseSkillSetRepository(SkillSetDAO dao) {
        this.dao = dao;
    }

    @Override
    public void add(SkillSet skillSet) {
        try {
            dao.insert(skillSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SkillSet> getAll() {
        try {
            return dao.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<SkillSet> findByPerson(Person person) {
        try {
            return dao.selectByPerson(person);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<SkillSet> findBySkill(Skill skill) {
        try {
            return dao.selectBySkill(skill);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<SkillSet> findByPersonAndSkill(Person person, Skill skill) {
        try {
            return dao.selectByPersonAndSkill(person, skill);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void remove(SkillSet skillSet) {
        try {
            dao.delete(skillSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
