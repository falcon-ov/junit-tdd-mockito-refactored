package dao;

import org.example.dao.SkillSetDAO;
import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SkillSetDAOTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private SkillSetDAO skillSetDAO;

    private Person person;
    private Skill skill;
    private SkillSet skillSet;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        skillSetDAO = new SkillSetDAO(mockConnection);

        person = new Person("John", "Doe", "john@example.com");
        skill = new Skill("Java", "Programming");
        skillSet = new SkillSet(person, skill, 5, 3);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    // ------------------ тесты ------------------

    @Test
    void testInsert() throws SQLException {
        skillSetDAO.insert(skillSet);
        verifyInsertParameters(skillSet);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testSelectAll() throws SQLException {
        prepareMockResultSet();
        List<SkillSet> result = skillSetDAO.selectAll();
        verifyResultSetResult(result);
    }

    @Test
    void testSelectByPerson() throws SQLException {
        prepareMockResultSet();
        List<SkillSet> result = skillSetDAO.selectByPerson(person);
        verifyResultSetResult(result);
        verify(mockPreparedStatement).setString(1, "John");
        verify(mockPreparedStatement).setString(2, "Doe");
        verify(mockPreparedStatement).setString(3, "john@example.com");
    }

    @Test
    void testSelectBySkill() throws SQLException {
        prepareMockResultSetForSkill();
        List<SkillSet> result = skillSetDAO.selectBySkill(skill);
        verifyResultSetResult(result);
        verify(mockPreparedStatement).setString(1, "Java");
        verify(mockPreparedStatement).setString(2, "Programming");
    }

    @Test
    void testSelectByPersonAndSkill() throws SQLException {
        prepareMockResultSetForLevelAndYears();
        Optional<SkillSet> optional = skillSetDAO.selectByPersonAndSkill(person, skill);
        assertTrue(optional.isPresent());
        SkillSet ss = optional.get();
        assertEquals(5, ss.getLevel());
        assertEquals(3, ss.getYearsExperience());
        verify(mockPreparedStatement).setString(1, "John");
        verify(mockPreparedStatement).setString(2, "Doe");
        verify(mockPreparedStatement).setString(3, "john@example.com");
        verify(mockPreparedStatement).setString(4, "Java");
        verify(mockPreparedStatement).setString(5, "Programming");
    }

    @Test
    void testDelete() throws SQLException {
        skillSetDAO.delete(skillSet);
        verifyDeleteParameters(skillSet);
        verify(mockPreparedStatement).executeUpdate();
    }

    // ------------------ приватные методы для моков ------------------

    private void prepareMockResultSet() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("person_name")).thenReturn("John");
        when(mockResultSet.getString("person_surname")).thenReturn("Doe");
        when(mockResultSet.getString("email")).thenReturn("john@example.com");
        when(mockResultSet.getString("skill_name")).thenReturn("Java");
        when(mockResultSet.getString("skill_domain")).thenReturn("Programming");
        when(mockResultSet.getInt("level")).thenReturn(5);
        when(mockResultSet.getInt("years_experience")).thenReturn(3);
    }

    private void prepareMockResultSetForPerson() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("skill_name")).thenReturn("Java");
        when(mockResultSet.getString("skill_domain")).thenReturn("Programming");
        when(mockResultSet.getInt("level")).thenReturn(5);
        when(mockResultSet.getInt("years_experience")).thenReturn(3);
    }

    private void prepareMockResultSetForLevelAndYears() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("level")).thenReturn(5);
        when(mockResultSet.getInt("years_experience")).thenReturn(3);
    }

    private void verifyResultSetResult(List<SkillSet> result) {
        assertEquals(1, result.size());
        SkillSet ss = result.get(0);
        assertEquals("John", ss.getPerson().getName());
        assertEquals("Java", ss.getSkill().getName());
        assertEquals(5, ss.getLevel());
        assertEquals(3, ss.getYearsExperience());
    }

    private void prepareMockResultSetForSkill() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("person_name")).thenReturn("John");
        when(mockResultSet.getString("person_surname")).thenReturn("Doe");
        when(mockResultSet.getString("email")).thenReturn("john@example.com");
        when(mockResultSet.getString("skill_name")).thenReturn("Java");
        when(mockResultSet.getString("skill_domain")).thenReturn("Programming");
        when(mockResultSet.getInt("level")).thenReturn(5);
        when(mockResultSet.getInt("years_experience")).thenReturn(3);
    }


    private void verifyInsertParameters(SkillSet ss) throws SQLException {
        verify(mockPreparedStatement).setString(1, ss.getPerson().getName());
        verify(mockPreparedStatement).setString(2, ss.getPerson().getSurname());
        verify(mockPreparedStatement).setString(3, ss.getPerson().getEmail());
        verify(mockPreparedStatement).setString(4, ss.getSkill().getName());
        verify(mockPreparedStatement).setString(5, ss.getSkill().getDomain());
        verify(mockPreparedStatement).setInt(6, ss.getLevel());
        verify(mockPreparedStatement).setInt(7, ss.getYearsExperience());
    }


    private void verifyDeleteParameters(SkillSet ss) throws SQLException {
        verify(mockPreparedStatement).setString(1, ss.getPerson().getName());
        verify(mockPreparedStatement).setString(2, ss.getPerson().getSurname());
        verify(mockPreparedStatement).setString(3, ss.getPerson().getEmail());
        verify(mockPreparedStatement).setString(4, ss.getSkill().getName());
        verify(mockPreparedStatement).setString(5, ss.getSkill().getDomain());
    }
}
