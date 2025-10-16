package repository.impl;

import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.example.repository.impl.InMemorySkillSetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemorySkillSetRepositoryTest {
    private InMemorySkillSetRepository skillSetRepository;
    Person person;
    Person diffPerson;
    Skill skill;
    Skill diffSkill;
    SkillSet skillSet;
    SkillSet skillSetDiffPerson;
    SkillSet skillSetDiffSkill;

    @BeforeEach
    public void beforeEach(){
        skillSetRepository = new InMemorySkillSetRepository();
        person = new Person("Name", "Surname","email@gmail.com");
        diffPerson= new Person("DiffName", "DiffSurname","Diffemail@gmail.com");
        skill = new Skill("Java","IT");
        diffSkill = new Skill("Golang","IT");
        skillSet = new SkillSet(person, skill, 10, 5);
        skillSetDiffSkill = new SkillSet(person, diffSkill, 10, 5);
        skillSetDiffPerson = new SkillSet(diffPerson, skill, 10, 5);
    }

    @Test
    public void testAdd(){
        skillSetRepository.add(skillSet);

        assertTrue(skillSetRepository.getAll().contains(skillSet));
        assertEquals(1, skillSetRepository.getAll().size());
    }

    @Test
    public void testGetAll(){
        addDefaultSkillSets();

        List<SkillSet> result = skillSetRepository.getAll();
        assertTrue(result.contains(skillSet)
                && result.contains(skillSetDiffSkill)
                && result.contains(skillSetDiffPerson));
        assertEquals(3, skillSetRepository.getAll().size());
    }

    @Test
    public void testFindByPerson(){
        addDefaultSkillSets();

        List<SkillSet> result = skillSetRepository.findByPerson(person);
        assertTrue(result.contains(skillSet) && result.contains(skillSetDiffSkill));
    }
    @Test
    public void testFindByPersonNotFound() {
        addDefaultSkillSets();
        List<SkillSet> result = skillSetRepository.findByPerson(new Person("NoName", "NoPerson", "Noemail@mail.com"));
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindBySkill(){
        addDefaultSkillSets();

        List<SkillSet> result = skillSetRepository.findBySkill(skill);
        assertTrue(result.contains(skillSet) && result.contains(skillSetDiffPerson));
    }

    @Test
    public void testFindBySkillNotFound() {
        addDefaultSkillSets();
        List<SkillSet> result = skillSetRepository.findBySkill(new Skill("Python", "IT"));
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindByPersonAndSkill(){
        addDefaultSkillSets();

        Optional<SkillSet> result = skillSetRepository.findByPersonAndSkill(person, skill);
        assertTrue(result.isPresent());
        assertEquals(skillSet, result.get());
    }

    @Test
    public void testFindByPersonAndSkillNotFound() {
        addDefaultSkillSets();
        Optional<SkillSet> result = skillSetRepository.findByPersonAndSkill(
                new Person("Noname", "Nosurname", "noemail@mail.com"),
                new Skill("Python", "IT")
        );
        assertTrue(result.isEmpty());
    }

    @Test
    public void testRemove(){
        addDefaultSkillSets();

        assertEquals(3, skillSetRepository.getAll().size());
        skillSetRepository.remove(skillSet);

        assertFalse(skillSetRepository.getAll().contains(skillSet));
        assertEquals(2, skillSetRepository.getAll().size());
    }

    private void addDefaultSkillSets() {
        skillSetRepository.add(skillSet);
        skillSetRepository.add(skillSetDiffSkill);
        skillSetRepository.add(skillSetDiffPerson);
    }

}
