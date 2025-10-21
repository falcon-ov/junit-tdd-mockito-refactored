package org.example.service;

import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.example.repository.SkillSetRepository;

import java.util.List;
import java.util.Optional;

public interface SkillSetService {
    void addSkillSet(SkillSet skillSet);
    Optional<Integer> getSkillLevel(Person person, Skill skill);
    List<SkillSet> getAllSkills(Person person);
    void removeSkill(Person person, Skill skill);
    double getRating(SkillSet skillSet);
    boolean isGood(SkillSet skillSet);
    void importFromCsv();
}
