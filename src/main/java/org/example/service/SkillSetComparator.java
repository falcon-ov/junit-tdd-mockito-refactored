package org.example.service;

import java.util.Comparator;

import org.example.model.SkillSet;
import org.example.service.impl.SkillSetServiceImpl;

public class SkillSetComparator implements Comparator<SkillSet> {
    private final SkillSetService service;

    public SkillSetComparator(SkillSetService service) {
        this.service = service;
    }

    public int compare(SkillSet a, SkillSet b) {
        double ratingA = service.getRating(a);
        double ratingB = service.getRating(b);
        return Double.compare(ratingA, ratingB);
    }
}
