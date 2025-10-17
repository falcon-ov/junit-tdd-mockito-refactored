package org.example.service.impl;

import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.example.repository.SkillSetRepository;
import org.example.service.SkillSetService;
import org.example.validation.Validator;

import java.util.List;
import java.util.Optional;

public class SkillSetServiceImpl implements SkillSetService {
    private final SkillSetRepository skillSetRepository;
    private final Validator<SkillSet> validator;

    public SkillSetServiceImpl(SkillSetRepository skillSetRepository, Validator<SkillSet> validator) {
        this.skillSetRepository = skillSetRepository;
        this.validator = validator;
    }

    public void addSkillSet(SkillSet skillSet) {
        List<String> errors = validator.validate(skillSet);
        if (!errors.isEmpty()) {
            errors.forEach(System.out::println);
        } else {
            skillSetRepository.add(skillSet);
        }
    }

    public Optional<Integer> getSkillLevel(Person person, Skill skill) {
        return skillSetRepository.findByPersonAndSkill(person, skill).map(SkillSet::getLevel);
    }

    public List<SkillSet> getAllSkills(Person person) {
        return skillSetRepository.findByPerson(person);
    }

    public void removeSkill(Person person, Skill skill) {
        skillSetRepository
                .findByPersonAndSkill(person, skill)
                .ifPresent(skillSetRepository::remove);
    }

    public double getRating(SkillSet skillSet) {
        return skillSet.getLevel() * 10 * ((double) skillSet.getYearsExperience() / 10 + 0.65);
    }

    public boolean isGood(SkillSet skillSet) {
        return getRating(skillSet) >= 50;
    }
}
