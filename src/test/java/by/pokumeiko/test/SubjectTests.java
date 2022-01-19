package by.pokumeiko.test;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import static org.assertj.core.api.Assertions.*;

import by.pokumeiko.project.Application;
import by.pokumeiko.project.entity.Subject;
import by.pokumeiko.project.repo.SubjectRepository;
import by.pokumeiko.project.service.SubjectService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class SubjectTests {

    @Autowired
    private SubjectService service;

    @Autowired
    private SubjectRepository repository;

    @Test
    public void testService() throws Exception {
    	
    	Subject subject_one = new Subject("детектив");
    	Subject subject_two = new Subject("сказка");
    	 
    	Subject subject1 = repository.save(subject_one);
    	Subject subject2 = repository.save(subject_two);
    	
   	   	Assert.notNull(subject1, "Error");
    	
   	   	assertThat(subject2.getName().equals(subject_two.getName()));
    	
    	assertEquals(2, service.listAllForBook().size());
    	
    	service.delete(subject1.getId());
    	
    	assertEquals(1, service.listAllForBook().size());
     }
}
