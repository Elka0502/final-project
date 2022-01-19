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
import by.pokumeiko.project.entity.Language;
import by.pokumeiko.project.repo.LanguageRepository;
import by.pokumeiko.project.service.LanguageService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class LanguageTests {

    @Autowired
    private LanguageService service;

    @Autowired
    private LanguageRepository repository;

    @Test
    public void testService() throws Exception {
    	
    	Language language_rus = new Language("Russian");
    	Language language_engl = new Language("English");
    	 
    	Language lang_rus = repository.save(language_rus);
    	Language lang_engl = repository.save(language_engl);
    	
   	   	Assert.notNull(lang_rus, "Error");
    	
   	   	assertThat(lang_engl.getName().equals(language_engl.getName()));
    	
     	assertEquals(2, service.listAllForBook().size());
    	
    	service.delete(lang_rus.getId());
    	
    	assertEquals(1, service.listAllForBook().size());
     }
}
