package org.example.validation;

import org.example.model.Skill;
import java.util.ArrayList;
import java.util.List;

public class SkillValidator implements Validator<Skill> {

    @Override
    public List<String> validate(Skill skill) {
        List<String> errors = new ArrayList<>();

        if (skill == null) {
            errors.add("Skill is null");
            return errors;
        }
        if (skill.getName() == null || skill.getName().isEmpty())
            errors.add("Skill name must not be empty");
        if (skill.getDomain() == null || skill.getDomain().isEmpty())
            errors.add("Skill domain must not be empty");

        return errors;
    }
}
