package by.pokumeiko.project.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.pokumeiko.project.Application;
import by.pokumeiko.project.entity.Publishing;
import by.pokumeiko.project.repo.BookRepository;
import by.pokumeiko.project.repo.PublishingRepository;

@Service
@Transactional
public class PublishingService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PublishingService.class);
	
	@Autowired
    private PublishingRepository publishingRepo;
	
	@Autowired
    private BookRepository bookRepo;
     
	/**Output a list of publishing houses where id!=1 order by title*/
    public Page<Publishing> listAll(Pageable pageable) {
    	return publishingRepo.findByIdNotOrderByName(1L, pageable);
    }
     
    /**Output a list of publishing houses order by title*/
    public List<Publishing> listAllForBook() {
    	return publishingRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
    
    public void save(Publishing publishing) {
    	publishingRepo.save(publishing);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d сохранил издательство id= %d в базе", Application.id_user, publishing.getId()));
    }
    
    /**Saving the publishing in the database*/
    public void save(Long id, String name) {
    	
    	Publishing publishing = get(id);
    	publishing.setName(name);
    	   	
    	publishingRepo.save(publishing);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d сохранил издательство id= %d в базе", Application.id_user, id));
     }
    
    public Publishing get(Long id) {
        return publishingRepo.findById(id).get();
    }
     
    /**Deleting the publishing from the database if there are no references to it in other database records*/
    public Boolean delete(Long id) {
    	
    	if (bookRepo.findByIdpublishing(id).isEmpty()) {
    		
    		publishingRepo.deleteById(id);
    		
    		LOGGER.info(String.format("Пользователь idUser= %d удалил издательство id= %d из базы", Application.id_user, id));
    		
    		return true;
		}
    	return false;
    }
}
