package org.example.dao;

import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillSetDAO {
    private final Connection connection;

    private static final String TABLE = "skillsets";
    private static final String COL_PERSON_NAME = "person_name";
    private static final String COL_PERSON_SURNAME = "person_surname";
    private static final String COL_EMAIL = "email";
    private static final String COL_SKILL_NAME = "skill_name";
    private static final String COL_SKILL_DOMAIN = "skill_domain";
    private static final String COL_LEVEL = "level";
    private static final String COL_YEARS_EXPERIENCE = "years_experience";

    public SkillSetDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(SkillSet skillSet) throws SQLException {
        String sql = "INSERT INTO " + TABLE + " (" +
                COL_PERSON_NAME + ", " + COL_PERSON_SURNAME + ", " + COL_EMAIL + ", " +
                COL_SKILL_NAME + ", " + COL_SKILL_DOMAIN + ", " + COL_LEVEL + ", " + COL_YEARS_EXPERIENCE +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setPersonParams(preparedStatement, skillSet.getPerson(), 1);
            setSkillParams(preparedStatement, skillSet.getSkill(), 4);
            preparedStatement.setInt(6, skillSet.getLevel());
            preparedStatement.setInt(7, skillSet.getYearsExperience());
            preparedStatement.executeUpdate();
        }
    }

    public List<SkillSet> selectAll() throws SQLException {
        String sql = "SELECT * FROM " + TABLE;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return mapRows(resultSet);
        }
    }

    public List<SkillSet> selectByPerson(Person person) throws SQLException {
        String sql = "SELECT * FROM " + TABLE + " WHERE " +
                COL_PERSON_NAME + " = ? AND " + COL_PERSON_SURNAME + " = ? AND " + COL_EMAIL + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setPersonParams(preparedStatement, person, 1);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return mapRows(resultSet);
            }
        }
    }

    public List<SkillSet> selectBySkill(Skill skill) throws SQLException {
        String sql = "SELECT * FROM " + TABLE + " WHERE " +
                COL_SKILL_NAME + " = ? AND " + COL_SKILL_DOMAIN + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setSkillParams(preparedStatement, skill, 1);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return mapRows(resultSet);
            }
        }
    }

    public Optional<SkillSet> selectByPersonAndSkill(Person person, Skill skill) throws SQLException {
        String sql = "SELECT * FROM " + TABLE + " WHERE " +
                COL_PERSON_NAME + " = ? AND " + COL_PERSON_SURNAME + " = ? AND " + COL_EMAIL + " = ?" +
                " AND " + COL_SKILL_NAME + " = ? AND " + COL_SKILL_DOMAIN + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setPersonParams(preparedStatement, person, 1);
            setSkillParams(preparedStatement, skill, 4);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<SkillSet> skillSets = mapRows(resultSet);
                return skillSets.isEmpty() ? Optional.empty() : Optional.of(skillSets.get(0));
            }
        }
    }

    public void delete(SkillSet skillSet) throws SQLException {
        String sql = "DELETE FROM " + TABLE + " WHERE " +
                COL_PERSON_NAME + " = ? AND " + COL_PERSON_SURNAME + " = ? AND " + COL_EMAIL + " = ?" +
                " AND " + COL_SKILL_NAME + " = ? AND " + COL_SKILL_DOMAIN + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setPersonParams(preparedStatement, skillSet.getPerson(), 1);
            setSkillParams(preparedStatement, skillSet.getSkill(), 4);
            preparedStatement.executeUpdate();
        }
    }

    private void setPersonParams(PreparedStatement preparedStatement, Person person, int startIndex) throws SQLException {
        preparedStatement.setString(startIndex, person.getName());
        preparedStatement.setString(startIndex + 1, person.getSurname());
        preparedStatement.setString(startIndex + 2, person.getEmail());
    }

    private void setSkillParams(PreparedStatement preparedStatement, Skill skill, int startIndex) throws SQLException {
        preparedStatement.setString(startIndex, skill.getName());
        preparedStatement.setString(startIndex + 1, skill.getDomain());
    }

    private SkillSet mapRow(ResultSet resultSet) throws SQLException {
        Person person = new Person(
                resultSet.getString(COL_PERSON_NAME),
                resultSet.getString(COL_PERSON_SURNAME),
                resultSet.getString(COL_EMAIL)
        );
        Skill skill = new Skill(
                resultSet.getString(COL_SKILL_NAME),
                resultSet.getString(COL_SKILL_DOMAIN)
        );
        int level = resultSet.getInt(COL_LEVEL);
        int years = resultSet.getInt(COL_YEARS_EXPERIENCE);
        return new SkillSet(person, skill, level, years);
    }

    private List<SkillSet> mapRows(ResultSet resultSet) throws SQLException {
        List<SkillSet> skillSets = new ArrayList<>();
        while (resultSet.next()) {
            skillSets.add(mapRow(resultSet));
        }
        return skillSets;
    }
}