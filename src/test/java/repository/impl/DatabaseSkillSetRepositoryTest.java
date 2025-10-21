package repository.impl;

import org.example.dao.SkillSetDAO;
import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.example.repository.impl.DatabaseSkillSetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseSkillSetRepositoryTest {

    private SkillSetDAO dao;
    private DatabaseSkillSetRepository repository;

    private Person person = new Person("John", "Doe", "john.doe@example.com");
    private Skill skill = new Skill("Java", "Programming");
    private SkillSet skillSet = new SkillSet(person, skill, 3, 5);

    @BeforeEach
    void setUp() {
        dao = mock(SkillSetDAO.class);
        repository = new DatabaseSkillSetRepository(dao);
    }

    @Test
    void add_shouldCallDaoInsert() throws SQLException {
        repository.add(skillSet);
        verify(dao, times(1)).insert(skillSet);
    }

    @Test
    void getAll_shouldReturnListFromDao() throws SQLException {
        List<SkillSet> expected = Arrays.asList(skillSet);
        when(dao.selectAll()).thenReturn(expected);

        List<SkillSet> actual = repository.getAll();
        assertEquals(expected, actual);
    }

    @Test
    void findByPerson_shouldReturnListFromDao() throws SQLException {
        List<SkillSet> expected = Arrays.asList(skillSet);
        when(dao.selectByPerson(person)).thenReturn(expected);

        List<SkillSet> actual = repository.findByPerson(person);
        assertEquals(expected, actual);
    }

    @Test
    void findBySkill_shouldReturnListFromDao() throws SQLException {
        List<SkillSet> expected = Arrays.asList(skillSet);
        when(dao.selectBySkill(skill)).thenReturn(expected);

        List<SkillSet> actual = repository.findBySkill(skill);
        assertEquals(expected, actual);
    }

    @Test
    void findByPersonAndSkill_shouldReturnOptionalFromDao() throws SQLException {
        Optional<SkillSet> expected = Optional.of(skillSet);
        when(dao.selectByPersonAndSkill(person, skill)).thenReturn(expected);

        Optional<SkillSet> actual = repository.findByPersonAndSkill(person, skill);
        assertEquals(expected, actual);
    }

    @Test
    void remove_shouldCallDaoDelete() throws SQLException {
        repository.remove(skillSet);
        verify(dao, times(1)).delete(skillSet);
    }

    @Test
    void getAll_shouldReturnEmptyOnException() throws SQLException {
        when(dao.selectAll()).thenThrow(new SQLException());

        List<SkillSet> result = repository.getAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void findByPersonAndSkill_shouldReturnEmptyOnException() throws SQLException {
        when(dao.selectByPersonAndSkill(person, skill)).thenThrow(new SQLException());

        Optional<SkillSet> result = repository.findByPersonAndSkill(person, skill);
        assertTrue(result.isEmpty());
    }
}
