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

    public SkillSetDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(SkillSet skillSet) throws SQLException {
        String sql = "INSERT INTO skillsets " +
                "(person_name, person_surname, email, skill_name, skill_domain, level, years_experience) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, skillSet.getPerson().getName());
            ps.setString(2, skillSet.getPerson().getSurname());
            ps.setString(3, skillSet.getPerson().getEmail());
            ps.setString(4, skillSet.getSkill().getName());
            ps.setString(5, skillSet.getSkill().getDomain());
            ps.setInt(6, skillSet.getLevel());
            ps.setInt(7, skillSet.getYearsExperience());
            ps.executeUpdate();
        }
    }

    public List<SkillSet> selectAll() throws SQLException {
        List<SkillSet> result = new ArrayList<>();
        String sql = "SELECT * FROM skillsets";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Person person = new Person(rs.getString("person_name"),
                        rs.getString("person_surname"),
                        rs.getString("email"));
                Skill skill = new Skill(rs.getString("skill_name"),
                        rs.getString("skill_domain"));
                int level = rs.getInt("level");
                int years = rs.getInt("years_experience");
                result.add(new SkillSet(person, skill, level, years));
            }
        }
        return result;
    }

    public List<SkillSet> selectByPerson(Person person) throws SQLException {
        List<SkillSet> result = new ArrayList<>();
        String sql = "SELECT * FROM skillsets WHERE person_name = ? AND person_surname = ? AND email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, person.getName());
            ps.setString(2, person.getSurname());
            ps.setString(3, person.getEmail());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Skill skill = new Skill(rs.getString("skill_name"), rs.getString("skill_domain"));
                int level = rs.getInt("level");
                int years = rs.getInt("years_experience");
                result.add(new SkillSet(person, skill, level, years));
            }
        }
        return result;
    }

    public List<SkillSet> selectBySkill(Skill skill) throws SQLException {
        List<SkillSet> result = new ArrayList<>();
        String sql = "SELECT * FROM skillsets WHERE skill_name = ? AND skill_domain = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, skill.getName());
            ps.setString(2, skill.getDomain());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Person person = new Person(rs.getString("person_name"),
                        rs.getString("person_surname"),
                        rs.getString("email"));
                int level = rs.getInt("level");
                int years = rs.getInt("years_experience");
                result.add(new SkillSet(person, skill, level, years));
            }
        }
        return result;
    }

    public Optional<SkillSet> selectByPersonAndSkill(Person person, Skill skill) throws SQLException {
        String sql = "SELECT * FROM skillsets WHERE person_name = ? AND person_surname = ? AND email = ? AND skill_name = ? AND skill_domain = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, person.getName());
            ps.setString(2, person.getSurname());
            ps.setString(3, person.getEmail());
            ps.setString(4, skill.getName());
            ps.setString(5, skill.getDomain());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int level = rs.getInt("level");
                int years = rs.getInt("years_experience");
                return Optional.of(new SkillSet(person, skill, level, years));
            }
        }
        return Optional.empty();
    }

    public void delete(SkillSet skillSet) throws SQLException {
        String sql = "DELETE FROM skillsets WHERE person_name = ? AND person_surname = ? AND email = ? AND skill_name = ? AND skill_domain = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, skillSet.getPerson().getName());
            ps.setString(2, skillSet.getPerson().getSurname());
            ps.setString(3, skillSet.getPerson().getEmail());
            ps.setString(4, skillSet.getSkill().getName());
            ps.setString(5, skillSet.getSkill().getDomain());
            ps.executeUpdate();
        }
    }
}
