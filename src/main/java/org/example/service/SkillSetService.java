package org.example.service;

import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.example.repository.SkillSetRepository;

import java.util.List;
import java.util.Optional;

public class SkillSetService {
    private SkillSetRepository skillSetrepository;

    public SkillSetService(SkillSetRepository skillSetrepository) {
        this.skillSetrepository = skillSetrepository;
    }

    public void addSkill(Person person, Skill skill, int level){
        SkillSet skillSet = new SkillSet(person, skill, level);
        skillSetrepository.add(skillSet);
    }

    public Optional<Integer> getSkillLevel(Person person, Skill skill){
        return skillSetrepository.findByPersonAndSkill(person, skill).map(SkillSet::getLevel);
    }

    public List<SkillSet> getAllSkills(Person person){
        return skillSetrepository.findByPerson(person);
    }

    public void removeSkill(Person person, Skill skill){
        skillSetrepository
                .findByPersonAndSkill(person, skill)
                .ifPresent(skillSetrepository::remove);
    }

}
