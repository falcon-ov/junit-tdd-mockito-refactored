package org.example.validation;

import org.example.model.Person;
import java.util.ArrayList;
import java.util.List;

public class PersonValidator implements Validator<Person> {

    @Override
    public List<String> validate(Person person) {
        List<String> errors = new ArrayList<>();

        if (person == null) {
            errors.add("Person is null");
            return errors;
        }
        if (person.getName() == null || person.getName().isEmpty())
            errors.add("Name must not be empty");
        if (person.getSurname() == null || person.getSurname().isEmpty())
            errors.add("Surname must not be empty");
        if (person.getEmail() == null || !person.getEmail().contains("@"))
            errors.add("Email must contain '@'");

        return errors;
    }
}
