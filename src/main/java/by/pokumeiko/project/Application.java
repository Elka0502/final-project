package by.pokumeiko.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	public static final int BUTTONS_TO_SHOW = 7;
	public static final int INITIAL_PAGE = 0;
	public static final int INITIAL_PAGE_SIZE = 5;
	public static final int[] PAGE_SIZES = {5, 10, 15, 20, 25, 30, 50, 100};
	    
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	public static Long id_user;
	
	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
		
		LOGGER.info("Start Application");
	}

}
