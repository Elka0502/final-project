package by.pokumeiko.project.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.pokumeiko.project.Application;
import by.pokumeiko.project.entity.Role;
import by.pokumeiko.project.entity.User;
import by.pokumeiko.project.repo.OrdersRepository;
import by.pokumeiko.project.repo.ReaderBookRepository;
import by.pokumeiko.project.repo.UserRepository;
import by.pokumeiko.project.securingweb.WebSecurityConfig;

@Service
@Transactional
public class AccessService {
 
	private static final Logger LOGGER = LoggerFactory.getLogger(AccessService.class);
	
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private ReaderBookRepository readerBookRepo;
    
    @Autowired
    private OrdersRepository ordersRepo;
    
    /**Saving a new user with a list of roles by the administrator*/
    public Boolean save(User user, List<Long> roleId) {
    	if (getUserbyName(user.getUsername()) == null)   {
    		
    		if(roleId.size() > 0){
		         for(Long id : roleId){
		
		        	 Role role = getRole(id);
		             user.addRole(role);
		         }
			 }
    		user.setUser_password(new WebSecurityConfig().passwordEncoder().encode(user.getUser_password()));
			userRepo.save(user);
			
			LOGGER.info(String.format ("Пользователь %d добавил пользователя с %s в базу", Application.id_user, user.getUsername()));
			return true;
    	} 
    	return false;
    }
    
    public void save(User user) {
    	userRepo.save(user);
    }
    
    /**Saving a new reader when registering*/
    public Boolean save(String userName, String passwordFirst, String passwordSecond) {
    	
    	if (passwordFirst.equals(passwordSecond)) {
    		
    		User user = new User();
    	    Role role = getRole(1L);
    	    user.addRole(role);
    	  
    		user.setUser_password(new WebSecurityConfig().passwordEncoder().encode(passwordFirst));
    		user.setUsername(userName);
    		userRepo.save(user);	
    		
    		LOGGER.info(String.format("Читатель %s зарегистрировался в системе", userName));
    		return true;   
		}
    	return false;
    }
 
    public Page<User> loadUserList(Pageable pageable) {
        return userRepo.findAllByOrderByUsername(pageable);
    }
 
    public List<Role> loadRoleList() {
        return userRepo.getRoleList();
    }
    
    public Role getRole(Long id) {
        return userRepo.getRoleById(id);
    }
    
    /**Deleting a user if he has no orders and books in the database*/
    public Boolean delete(Long id) {
    	
       if ((readerBookRepo.findByIdUser(id).isEmpty()) && (ordersRepo.findByIdUser(id).isEmpty()) && (id != Application.id_user)) {
    	 
    	   userRepo.deleteById(id);
    	   
    	   LOGGER.info(String.format ("Пользователь %d удалил пользователя с id= %d из базы", Application.id_user, id));
    	   return true;
       } 
       return false;
    }
    
    public User getUserbyName(String username) {
    	return userRepo.findUserByUsername(username);
    }
}
