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
import by.pokumeiko.project.entity.KindOfBook;
import by.pokumeiko.project.repo.BookRepository;
import by.pokumeiko.project.repo.KindOfBookRepository;

@Service
@Transactional
public class KindOfBookService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KindOfBookService.class);
	
	@Autowired
    private KindOfBookRepository kindOfBookRepo;
	
	@Autowired
    private BookRepository bookRepo;
     
	/**Output a list of kinds of books where id!=1 order by title*/
    public Page<KindOfBook> listAll(Pageable pageable) {
    	return kindOfBookRepo.findByIdNotOrderByName(1L, pageable);
    }
    
    /**Output a list of kinds of books order by title*/
    public List<KindOfBook> listAllForBook() {
      	return kindOfBookRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
    
    public void save(KindOfBook kindOfBook) {
    	
    	kindOfBookRepo.save(kindOfBook);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d сохранил вид издания id= %d в базе", Application.id_user, kindOfBook.getId()));
    }
    
    /**Saving the kind of book in the database*/
    public void save(Long id, String name) {
    	
    	KindOfBook kindOfBook = get(id);
    	kindOfBook.setName(name);
     		
    	kindOfBookRepo.save(kindOfBook);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d сохранил вид издания id= %d в базе", Application.id_user, id));
    }
   
    public KindOfBook get(Long id) {
        return kindOfBookRepo.findById(id).get();
    }
     
    /**Deleting the kind of book from the database if there are no references to it in other database records*/
    public Boolean delete(Long id) {
    	
    	if (bookRepo.findByIdkindOfBook(id).isEmpty()) {
			
    		kindOfBookRepo.deleteById(id);
    		
    		LOGGER.info(String.format("Пользователь idUser= %d удалил вид издания id= %d из базы", Application.id_user, id));
    		
    		return true;  
		}
    	return false; 
    }
}
