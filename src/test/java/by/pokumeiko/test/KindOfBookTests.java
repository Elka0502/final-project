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
import by.pokumeiko.project.entity.KindOfBook;
import by.pokumeiko.project.repo.KindOfBookRepository;
import by.pokumeiko.project.service.KindOfBookService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class KindOfBookTests {

    @Autowired
    private KindOfBookService service;

    @Autowired
    private KindOfBookRepository repository;

    @Test
    public void testService() throws Exception {
    	
    	KindOfBook kindOfBook_one = new KindOfBook("книга");
    	KindOfBook kindOfBook_engl = new KindOfBook("Сборник");
    	 
    	KindOfBook kindOfBook1 = repository.save(kindOfBook_one);
    	KindOfBook kindOfBook2 = repository.save(kindOfBook_engl);
    	
   	   	Assert.notNull(kindOfBook1, "Error");
    	
   	   	assertThat(kindOfBook2.getName().equals(kindOfBook_engl.getName()));
    	
     	assertEquals(2, service.listAllForBook().size());
    	
    	service.delete(kindOfBook1.getId());
    	
    	assertEquals(1, service.listAllForBook().size());
     }
}
