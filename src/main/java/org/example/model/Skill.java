package org.example.model;

import java.util.Objects;

public class Skill {
    private String name;
    private String domain;

    public Skill(String name, String domain){
        this.name = name;
        this.domain = domain;
    }
    public String getName() {
        return name;
    }
    public String getDomain() {
        return domain;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Skill other = (Skill)o;
        return Objects.equals(name, other.name) && Objects.equals(domain, other.domain);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, domain);
    }

}
