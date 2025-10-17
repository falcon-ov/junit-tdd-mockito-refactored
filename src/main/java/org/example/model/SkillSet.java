package org.example.model;

import java.util.Objects;

public class SkillSet {
    private Person person;
    private Skill skill;
    private int level;
    private int yearsExperience;

    public SkillSet(Person person, Skill skill, int level, int yearsExperience) {
        this.person = person;
        this.skill = skill;
        this.level = level;
        this.yearsExperience = yearsExperience;
    }

    public Person getPerson() {
        return person;
    }

    public Skill getSkill() {
        return skill;
    }

    public int getLevel() {
        return level;
    }

    public int getYearsExperience() {
        return yearsExperience;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setYearsExperience(int yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillSet other = (SkillSet) o;
        return Objects.equals(person, other.person) && Objects.equals(skill, other.skill) && Objects.equals(level, other.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, skill, level);
    }

}
