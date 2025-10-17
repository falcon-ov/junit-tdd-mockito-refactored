package service.impl;

import org.example.model.Person;
import org.example.model.Skill;
import org.example.model.SkillSet;
import org.example.repository.SkillSetRepository;
import org.example.service.SkillSetService;
import org.example.service.impl.SkillSetServiceImpl;
import org.example.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SkillSetServiceImplTest {
    Person person;
    Skill skill;
    SkillSet skillSet;
    SkillSetRepository mockRepo;
    Validator<SkillSet> mockValidator;
    SkillSetService service;

    @BeforeEach
    public void beforeEach(){
        person = new Person("Name", "Surname", "email@gmail.com");
        skill = new Skill("Java", "IT");
        skillSet = new SkillSet(person, skill, 10, 5);
        mockRepo = Mockito.mock(SkillSetRepository.class);
        mockValidator = Mockito.mock(Validator.class);
        service = new SkillSetServiceImpl(mockRepo, mockValidator);
    }

    @Test
    public void testAddSkillSet(){
        service.addSkillSet(skillSet);

        verify(mockRepo, times(1)).add(any(SkillSet.class));
    }

    @Test
    public void testGetSkillLevel(){
        when(mockRepo.findByPersonAndSkill(person, skill)).thenReturn(Optional.of(skillSet));

        Optional<Integer> level = service.getSkillLevel(person, skill);

        verify(mockRepo, times(1)).findByPersonAndSkill(person, skill);
        assertEquals(Optional.of(10), level);
    }

    @Test
    public void testGetSkillLevelNotFound(){
        when(mockRepo.findByPersonAndSkill(person, skill)).thenReturn(Optional.empty());

        Optional<Integer> level = service.getSkillLevel(person, skill);

        verify(mockRepo, times(1)).findByPersonAndSkill(person, skill);
        assertEquals(Optional.empty(), level);
    }

    @Test
    public void testGetAllSkills(){
        when(mockRepo.findByPerson(person)).thenReturn(List.of(skillSet));

        List<SkillSet> result = service.getAllSkills(person);

        verify(mockRepo, times(1)).findByPerson(person);
        assertEquals(1, result.size());
        assertEquals(List.of(skillSet), result);
    }

    @Test
    public void testRemoveSkill(){
        when(mockRepo.findByPersonAndSkill(person, skill)).thenReturn(Optional.of(skillSet));

        service.removeSkill(person, skill);

        verify(mockRepo, times(1)).findByPersonAndSkill(person, skill);
        verify(mockRepo, times(1)).remove(skillSet);
    }


}
