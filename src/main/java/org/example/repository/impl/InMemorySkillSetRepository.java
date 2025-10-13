package org.example.repository.impl;

import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.example.repository.SkillSetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemorySkillSetRepository implements SkillSetRepository {
    private List<SkillSet> skillSets = new ArrayList<>();

    @Override
    public void add(SkillSet skillSet) {
        skillSets.add(skillSet);
    }

    @Override
    public List<SkillSet> getAll() {
        return new ArrayList<>(skillSets);
    }

    @Override
    public List<SkillSet> findByPerson(Person person) {
        return skillSets.stream()
                .filter(s -> s.getPerson().equals(person))
                .toList();
    }

    @Override
    public List<SkillSet> findBySkill(Skill skill) {
        return skillSets.stream()
                .filter(s -> s.getSkill().equals(skill))
                .toList();
    }

    @Override
    public Optional<SkillSet> findByPersonAndSkill(Person person, Skill skill){
        return skillSets.stream()
                .filter(s -> s.getSkill().equals(skill) && s.getPerson().equals(person))
                .findFirst();
    }

    @Override
    public void remove(SkillSet skillSet) {
        skillSets.remove(skillSet);
    }

}
