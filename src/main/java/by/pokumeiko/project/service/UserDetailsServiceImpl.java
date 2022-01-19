package by.pokumeiko.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import by.pokumeiko.project.Application;
import by.pokumeiko.project.entity.User;
import by.pokumeiko.project.repo.UserRepository;

 
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepo;
     
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
    	
    	User user = null;
    	
    	if (!username.isEmpty()) {
    		 user = userRepo.findUserByUsername(username);
    	     Application.id_user =  user.getId();
    	}
       
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new MyUserDetails(user);
    }
    
    public List<User> loadUserList() {
         return userRepo.findAll();
    }
}
