package model;

import org.example.model.Person;
import org.example.model.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SkillTest {
    Skill original;
    Skill copy1;
    Skill copy2;
    Skill different;

    @BeforeEach
    public void beforeEach(){
        original = new Skill("Name", "Domain");
        copy1 = new Skill("Name", "Domain");
        copy2 = new Skill("Name", "Domain");
        different = new Skill("DiffName", "DiffDomain");
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
        assertFalse(new Skill(null, null).isValid());
    }
}
