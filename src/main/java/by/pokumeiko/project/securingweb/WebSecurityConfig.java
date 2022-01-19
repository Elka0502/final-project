package by.pokumeiko.project.securingweb;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import by.pokumeiko.project.service.UserDetailsServiceImpl;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
	
	
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
 
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/css/*.*", "/assets/**", "/vendor/**");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	  http.authorizeRequests()
    	  
         .antMatchers("/access/**").hasAnyAuthority("ADMIN")
         .antMatchers("/profile/reader/**").hasAnyAuthority("READER")

         .antMatchers("/", "/files/**", "/error/**", "/news/show/**", "/contacts/**", "/catalog/**", "/person/newPasswordLogin/**", "/person/newLogin/**", "/catalog", "/index", "/assets/**", "/vendor/**", "/login").permitAll()   
    
         .anyRequest().authenticated()
	     .and()
	       
	     .formLogin()
	     .loginPage("/login")
	     .permitAll()
	     .and()
	     
	     .logout().permitAll()
	     .and();
	 }
}