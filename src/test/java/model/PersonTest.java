package model;

import org.example.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    Person original;
    Person copy1;
    Person copy2;
    Person different;

    @BeforeEach
    public void beforeEach(){
        original = new Person("Name", "Surname","email@gmail.com");
        copy1 = new Person("Name", "Surname","email@gmail.com");
        copy2 = new Person("Name", "Surname","email@gmail.com");
        different = new Person("DiffName", "DiffSurname","Diffemail@gmail.com");
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
}
