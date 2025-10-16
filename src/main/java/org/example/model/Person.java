package org.example.model;

import java.util.Objects;

public class Person {
    private String name;
    private String surname;
    private String email;


    public Person(String name, String surname, String email){
        this.name = name;
        this.surname = surname;
        this.email = email;

    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getEmail() {
        return email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person other = (Person) o;
        return Objects.equals(name, other.name)
                && Objects.equals(surname, other.surname)
                && Objects.equals(email, other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, email);
    }

    public boolean isValid() {
        return name != null && !name.isEmpty()
                && surname != null && !surname.isEmpty()
                && email != null && email.contains("@");
    }
}
