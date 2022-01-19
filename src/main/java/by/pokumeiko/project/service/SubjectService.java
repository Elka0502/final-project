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
import by.pokumeiko.project.entity.Subject;
import by.pokumeiko.project.repo.BookRepository;
import by.pokumeiko.project.repo.SubjectRepository;

@Service
@Transactional
public class SubjectService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SubjectService.class);
	
	@Autowired
    private SubjectRepository subjectRepo;
	
	@Autowired
    private BookRepository bookRepo;
     
	/**Output a list of subjects where id!=1 order by title*/
    public Page<Subject> listAll(Pageable pageable) {
    	return subjectRepo.findByIdNotOrderByName(1L, pageable);
    }
   
    /**Output a list of subjects order by title*/
    public List<Subject> listAllForBook() {
    	return subjectRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
    
    public void save(Subject subject) {
    	
    	subjectRepo.save(subject);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d сохранил тематику id= %d в базе", Application.id_user, subject.getId()));
    }
    
    /**Saving the subject in the database*/
    public void save(Long id, String name) {
    	
    	Subject subject = get(id);
		subject.setName(name);
		
		subjectRepo.save(subject);
		
		LOGGER.info(String.format("Пользователь idUser= %d сохранил тематику id= %d в базе", Application.id_user, id));
    }
       
    public Subject get(Long id) {
        return subjectRepo.findById(id).get();
    }
     
    /**Deleting the subject from the database if there are no references to it in other database records*/
    public Boolean delete(Long id) {
    	
    	if (bookRepo.findByIdsubject(id).isEmpty()) {
    		
    		subjectRepo.deleteById(id);
    		
    		LOGGER.info(String.format("Пользователь idUser= %d удалил тематику id= %d из базы", Application.id_user, id));
    		
   			return true;  
   		}
    	return false;
    }
}
