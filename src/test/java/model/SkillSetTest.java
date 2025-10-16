package model;

import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SkillSetTest {
    SkillSet original;
    SkillSet copy1;
    SkillSet copy2;
    SkillSet different;
    Person person;
    Person diffPerson;
    Skill skill;
    Skill diffSkill;

    @BeforeEach
    public void beforeEach(){
        person = new Person("Name", "Surname","email@gmail.com");
        diffPerson = new Person("DiffName", "DiffSurname","Diffemail@gmail.com");
        skill = new Skill("Java", "IT");
        diffSkill = new Skill("Golang", "IT");

        original = new SkillSet(person, skill, 10, 5);
        copy1 = new SkillSet(person, skill,10, 5);
        copy2 = new SkillSet(person, skill,10, 5);
        different = new SkillSet(diffPerson, diffSkill,5, 2);
    }

    @Test
    public void testEqualsReflexivity(){
        assertEquals(original, original);
    }
    @Test
    public void testEqualsSymmetry(){
        assertEquals(original, copy1);
        assertEquals(copy1, original);
    }
    @Test
    public void testEqualsTransitivity(){
        assertEquals(original, copy1);
        assertEquals(copy1, copy2);
        assertEquals(original, copy2);
    }

    @Test
    public void testEqualsConsistency(){
        assertEquals(original, copy1);
        assertEquals(original, copy1);
    }

    @Test
    public void testEqualsNull(){
        assertFalse(original.equals(null));
    }

    @Test
    public void testEqualsDifferent(){
        assertNotEquals(original, different);
    }

    @Test
    public void testHashCodeConsistency(){
        int result1 = original.hashCode();
        int result2 = original .hashCode();
        assertEquals(result1, result2);

    }
    @Test
    public void testHashCodeConsistencyEquals(){
        assertEquals(original.hashCode(), copy1.hashCode());
    }

    @Test
    public void testHashCodeDifferentObjects() {
        assertNotEquals(original.hashCode(), different.hashCode());
    }

    @Test
    public void testIsValid(){
        assertTrue(original.isValid());
    }

    @Test
    public void testIsInvalid(){
        assertFalse(new SkillSet(null, null, -10, -10).isValid());
    }

    @Test
    public void testGetRating(){
        assertEquals(original.getLevel() * 10 * (original.getYearsExperience() / 10 + 0.65), original.getRating());
    }

    @Test
    public void testIsGood(){
        assertTrue(original.isGood());
    }
}
