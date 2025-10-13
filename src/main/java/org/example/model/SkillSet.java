package org.example.model;

import java.util.Objects;

public class SkillSet {
    private Person person;
    private Skill skill;
    private int level;

    public SkillSet(Person person, Skill skill, int level){
        this.person = person;
        this.skill = skill;
        this.level = level;
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
    public void setPerson(Person person) {
        this.person = person;
    }
    public void setSkill(Skill skill) {
        this.skill = skill;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        SkillSet other = (SkillSet)o;
        return Objects.equals(person, other.person)
                && Objects.equals(skill, other.skill)
                && Objects.equals(level, other.level);
    }
    @Override
    public int hashCode(){
        return Objects.hash(person, skill, level);
    }
}
