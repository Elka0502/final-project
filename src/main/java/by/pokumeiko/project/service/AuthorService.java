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
import by.pokumeiko.project.entity.Author;
import by.pokumeiko.project.repo.AuthorRepository;
import by.pokumeiko.project.repo.BookRepository;

@Service
@Transactional
public class AuthorService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorService.class);
	
	@Autowired
    private AuthorRepository authorRepo;
	
	@Autowired
    private BookRepository bookRepo;
	     
	/**Output a list of authors order by surname, first name*/
    public Page<Author> listAll(Pageable pageable) {
     	return authorRepo.findByIdNotOrderByFnameAscInameAscOnameAsc(1L, pageable);
    }
    
    /**Output a list of authors order by surname, first name*/
    public List<Author> listAll() {
     	return authorRepo.findByIdNotOrderByFnameAscInameAscOnameAsc(1L);
    }
     
    /**Saving the author in the database*/
    public void save(Long id, String fname, String iname, String oname) {
    	
    	Author author = getAuthor(id);
		author.setFname(fname);
		author.setIname(iname);
		author.setOname(oname);
		
		authorRepo.save(author);
		
		LOGGER.info(String.format("Пользователь idUser= %d сохранил автора %s в базе", Application.id_user, author.getFio()));
    }
    
    public void save(Author author) {
     	
    	authorRepo.save(author);
  	
    	LOGGER.info(String.format("Пользователь idUser= %d сохранил автора %s в базе", Application.id_user, author.getFname() + " " + author.getFname()));
    }
 
    /**Deleting the author if there is no binding to the book*/
    public Boolean delete(Long id) {
  
    	if (bookRepo.countAllById(id) == 0) {
    		authorRepo.deleteById(id);
    		
    		LOGGER.info(String.format("Пользователь idUser= %d удалил автора с id= %d из базы", Application.id_user, id));
    		
    		return true;
		}
 		return false;
   }
    
    public Author getAuthor(Long id) {
        return authorRepo.findByIdIs(id); 
    }
  }
