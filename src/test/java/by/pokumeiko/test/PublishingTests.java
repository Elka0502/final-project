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
import by.pokumeiko.project.entity.Publishing;
import by.pokumeiko.project.repo.PublishingRepository;
import by.pokumeiko.project.service.PublishingService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class PublishingTests {

    @Autowired
    private PublishingService service;

    @Autowired
    private PublishingRepository repository;

    @Test
    public void testService() throws Exception {
    	
    	Publishing publishing_one = new Publishing("Москва");
    	Publishing publishing_two = new Publishing("СПБ");
    	 
    	Publishing publishing1 = repository.save(publishing_one);
    	Publishing publishing2 = repository.save(publishing_two);
    	
   	   	Assert.notNull(publishing1, "Error");
    	
   	   	assertThat(publishing2.getName().equals(publishing2.getName()));;
    	
    	assertEquals(2, service.listAllForBook().size());
    	
    	service.delete(publishing1.getId());
    	
    	assertEquals(1, service.listAllForBook().size());
     }
}
