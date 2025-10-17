package org.example.service;

import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.example.repository.SkillSetRepository;

import java.util.List;
import java.util.Optional;

public interface SkillSetService {
    public void addSkillSet(SkillSet skillSet);
    public Optional<Integer> getSkillLevel(Person person, Skill skill);
    public List<SkillSet> getAllSkills(Person person);
    public void removeSkill(Person person, Skill skill);
}
