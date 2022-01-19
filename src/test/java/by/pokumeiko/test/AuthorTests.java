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
import by.pokumeiko.project.entity.Author;
import by.pokumeiko.project.repo.AuthorRepository;
import by.pokumeiko.project.service.AuthorService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class AuthorTests {

    @Autowired
    private AuthorService service;

    @Autowired
    private AuthorRepository repository;

    @Test
    public void testService() throws Exception {
    	
    	Author author_one = new Author("Пушкин", "Александр", "Сергеевич");
    	Author author_two = new Author("Толстой", "Лев", "");
    	 
    	Author author1 = repository.save(author_one);
    	Author author2 = repository.save(author_two);
    	
   	   	Assert.notNull(author1, "Error");
   	   	Assert.notNull(author2, "Error");
   	 
   	   	assertThat(author2.getFname().equals(author_two.getFname()));
   	   	assertThat(author2.getIname().equals(author_two.getIname()));
   	   	assertThat(author2.getOname().equals(author_two.getOname()));
   	   	
       	assertEquals(1, service.listAll().size());
    	
    	service.delete(author1.getId());
    	
    	assertEquals(1, service.listAll().size());
     }
}
