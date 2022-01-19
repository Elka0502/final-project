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
import by.pokumeiko.project.entity.User;
import by.pokumeiko.project.repo.UserRepository;
import by.pokumeiko.project.service.AccessService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class AccessTests {

    @Autowired
    private AccessService service;

    @Autowired
    private UserRepository repository;

    @Test
    public void testService() throws Exception {
      		
    	assertThat(service.save("user1", "1", "1"));
    	assertThat(service.save("user2", "2", "2"));
    	assertThat(service.save("user3", "3", "3"));
   	 
    	Assert.notNull(service.getUserbyName("user1"), "Error");
    	Assert.notNull(service.getUserbyName("user2"), "Error");
    	Assert.notNull(service.getUserbyName("user3"), "Error");
    	
    	User user = repository.findUserByUsername("user1");
    	    	
     	assertEquals(3, repository.findAll().size());
    	
     	service.delete(user.getId());
    	
    	assertEquals(2, repository.findAll().size());
     }
}
