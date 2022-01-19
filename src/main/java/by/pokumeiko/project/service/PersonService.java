package by.pokumeiko.project.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.pokumeiko.project.Application;
import by.pokumeiko.project.entity.Person;
import by.pokumeiko.project.entity.User;
import by.pokumeiko.project.repo.PersonRepository;
import by.pokumeiko.project.repo.UserRepository;
import by.pokumeiko.project.securingweb.WebSecurityConfig;

@Service
@Transactional
public class PersonService {
 
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
	
    @Autowired
    private PersonRepository personRepo;
    
    @Autowired
    private UserRepository userRepo;
    
    /**Saving the user's personal data*/
    public void save(Person person) {
    	
    	personRepo.save(person);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d изменил личные данные пользователя id= %d в базе", Application.id_user, person.getId()));
    }
        
    public Person get(Long id) {
       return personRepo.findById(id).get();
    }
     
    /**Deleting a user from the database*/
    public void delete(Long id) {
    	
    	personRepo.deleteById(id);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d удалил пользователя id= %d из базы", Application.id_user, id));
    }
    
    /**Saving a new password*/
    public Boolean saveNewPass(String passwordFirst, String passwordSecond) {
    	
    	if (passwordFirst.equals(passwordSecond)) {
    		
    		personRepo.saveNewPassword(new WebSecurityConfig().passwordEncoder().encode(passwordFirst), Application.id_user);
    		
    		LOGGER.info(String.format("Пользователь idUser= %d изменил пароль в базе", Application.id_user));
    		
    		return true;
		} 
    	return false;
    }
    
    /**Saving a new user with a password*/
    public void saveNewUser(String userName, String pass) {
    	
    	personRepo.saveNewUser(userName, pass);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d сохранил пользователя userName=%s в базе", Application.id_user, userName));
    }
    
    /**Saving a account data by reader */
    public void saveAccount(String email, String phone, Long id) {
    	
    	personRepo.saveAccount(email, phone, id);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d сохранил данные аккаунта в базе", Application.id_user));
    }
    
    public User getUserByUserName(String userName) {
    	return userRepo.findUserByUsername(userName);
    }
    
    /**Output a list of readers*/
    public Page<Person> getReaders(Pageable pageable) {
    	return personRepo.getReaders(pageable);
    }
}