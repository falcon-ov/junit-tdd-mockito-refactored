package org.example.io;

import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;

import java.io.*;
import java.util.*;

public class CsvSkillSetReader implements Reader<SkillSet> {

    @Override
    public List<SkillSet> readAll(String filePath) {
        List<SkillSet> skillSets = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                // допустим CSV: name,surname,email,skillName,skillDomain,level,yearsExperience
                Person person = new Person(parts[0], parts[1], parts[2]);
                Skill skill = new Skill(parts[3], parts[4]);
                int level = Integer.parseInt(parts[5]);
                int years = Integer.parseInt(parts[6]);
                skillSets.add(new SkillSet(person, skill, level, years));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return skillSets;
    }
}