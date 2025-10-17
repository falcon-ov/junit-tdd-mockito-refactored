package org.example.validation;

import org.example.model.SkillSet;
import java.util.ArrayList;
import java.util.List;

public class SkillSetValidator implements Validator<SkillSet> {

    private final PersonValidator personValidator = new PersonValidator();
    private final SkillValidator skillValidator = new SkillValidator();

    @Override
    public List<String> validate(SkillSet skillSet) {
        List<String> errors = new ArrayList<>();

        if (skillSet == null) {
            errors.add("SkillSet is null");
            return errors;
        }

        if (skillSet.getLevel() <= 0 || skillSet.getLevel() > 10)
            errors.add("Level must be between 1 and 10");

        if (skillSet.getYearsExperience() < 0)
            errors.add("Years of experience cannot be negative");

        errors.addAll(personValidator.validate(skillSet.getPerson()));
        errors.addAll(skillValidator.validate(skillSet.getSkill()));

        return errors;
    }
}
