package by.pokumeiko.project.service;

import java.util.ArrayList;
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
import by.pokumeiko.project.entity.Book;
import by.pokumeiko.project.repo.BookRepository;
import by.pokumeiko.project.repo.ReaderBookRepository;

@Service
@Transactional
public class BookService {
 
	private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
	
    @Autowired
    private BookRepository bookRepo;
    
    @Autowired
    private ReaderBookRepository readerBookRepo;
    
    @Autowired
	private AuthorService authorService;
    
    /**Displaying a list of books order by title*/
    public Page<Book> listAll(Pageable pageable) {
      	return bookRepo.findAllByOrderByIdDesc(pageable);
    }
     
    /**Saving a book and a list of authors to the database*/
    public void save(Book book, List <Long> authorId) {
    	
    	if  (authorId != null){
	        for(Long id : authorId){
	        	
	        	Author author = authorService.getAuthor(id);
	            book.addAuthor(author);
	        }
		 }
    	bookRepo.save(book);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d сохранил книгу id= %d в базе", Application.id_user, book.getId()));
    }
   
    /**Saving a book and a list of authors to the database*/
    public void save(Long id, List<Long> authorId, String name, Long idpublishing, Long idkindOfBook, Long idsubject, 
    				 Long idlanguage, Integer year, Integer count) {
    	
    	Book book = get(id);
		
		book.clearAuthor();
		if  (authorId != null){
	        for(Long idAu : authorId){
	        	Author author = authorService.getAuthor(idAu);
	            book.addAuthor(author);
	        }
		 }
		book.setName(name);
		book.setIdpublishing(idpublishing);
		book.setIdkindOfBook(idkindOfBook);
		book.setIdsubject(idsubject);
		book.setIdlanguage(idlanguage);
		book.setYear(year);
		book.setCount(count);
		
		bookRepo.save(book);
		
		LOGGER.info(String.format("Пользователь idUser= %d сохранил книгу id= %d в базе", Application.id_user, id));
    }
 
    public Book get(Long id) {
        return bookRepo.findById(id).get();
    }
     
    /**Deleting a book from the database if there are no references to it in other database records*/
    public Boolean delete(Long id) {
    	
    	if (readerBookRepo.findByIdBook(id).isEmpty()) {
    		
    		bookRepo.deleteById(id);
    		
    		LOGGER.info(String.format("Пользователь idUser= %d удалил книгу id= %d из базы", Application.id_user, id));
    		
    		return true;
		}
    	return false;
     }
    
    /**Search for a book in the catalog by the specified parameters*/
    public List<Book> getSearch(String author_name, String book_name, Long id_publishing, Long id_kind_of_book, Long id_subject, Long id_language, Integer year) {
    	
    	List<Book> bookList = new ArrayList<Book>();
    	
    	for (Long id: bookRepo.search(author_name, book_name, id_publishing, id_kind_of_book, id_subject, id_language, year)) {
			bookList.add(get(id));
		}
       	return bookList;
    }
     
    /**Search for a book in the database when issuing by number, author and title*/
    public List<Book> find(String numBook, String author, String bookName) {
    	
    	 List<Book> bookList = new ArrayList<Book>();
						
		 for (Long idBook: bookRepo.find(numBook.trim().toUpperCase(), author.trim().toUpperCase(), bookName.trim().toUpperCase())) {
			bookList.add(get(idBook));
		 }
		return bookList;
    }
}