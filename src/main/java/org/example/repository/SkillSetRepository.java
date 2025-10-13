package org.example.repository;

import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;

import java.util.List;
import java.util.Optional;

public interface SkillSetRepository {
    void add(SkillSet skillSet);
    List<SkillSet> getAll();
    List<SkillSet> findByPerson(Person person);
    List<SkillSet> findBySkill(Skill skill);
    Optional<SkillSet> findByPersonAndSkill(Person person, Skill skill);
    void remove(SkillSet skillSet);
}
