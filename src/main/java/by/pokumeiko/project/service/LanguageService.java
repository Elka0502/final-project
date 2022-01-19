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
import by.pokumeiko.project.entity.Language;
import by.pokumeiko.project.repo.BookRepository;
import by.pokumeiko.project.repo.LanguageRepository;

@Service
@Transactional
public class LanguageService {
 
	private static final Logger LOGGER = LoggerFactory.getLogger(LanguageService.class);
	
    @Autowired
    private LanguageRepository languageRepo;
    
    @Autowired
    private BookRepository bookRepo;
    
    /**Output a list of languages where id!=1 order by title*/
    public Page<Language> listAll(Pageable pageable) {
      	return languageRepo.findByIdNotOrderByName(1L, pageable);
    }
    
    /**Output a list of languages order by title*/
    public List<Language> listAllForBook() {
      	return languageRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
     
    public void save(Language language) {
    	
    	languageRepo.save(language);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d сохранил язык id= %d в базе", Application.id_user, language.getId()));
    }
   
    /**Saving the language in the database*/
    public void save(Long id, String name) {
    	
    	Language language = get(id);
		language.setName(name);
		
		languageRepo.save(language);
		
		LOGGER.info(String.format("Пользователь idUser= %d сохранил язык id= %d в базе", Application.id_user, id));
    }
    
    public Language get(Long id) {
        return languageRepo.findById(id).get();
    }
     
    /**Deleting the language from the database if there are no references to it in other database records*/
    public Boolean delete(Long id) {
    	
    	if (bookRepo.findByIdlanguage(id).isEmpty()) {
    		
    		languageRepo.deleteById(id);
    		
    		LOGGER.info(String.format("Пользователь idUser= %d удалил язык id= %d из базы", Application.id_user, id));
    		
    		return true;  
		}
		return false;  
    }
}
