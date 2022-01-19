package by.pokumeiko.project.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import by.pokumeiko.project.Application;
import by.pokumeiko.project.entity.Orders;
import by.pokumeiko.project.entity.ReaderBook;
import by.pokumeiko.project.repo.PersonRepository;
import by.pokumeiko.project.repo.ReaderBookRepository;

@Service
@Transactional
public class ReaderBookService {
 
	private static final Logger LOGGER = LoggerFactory.getLogger(ReaderBookService.class);
	
    @Autowired
    private ReaderBookRepository readerBookRepo;
    
    @Autowired
    private PersonRepository personRepo;
    
    @Autowired
    private OrdersService ordersService;
     
    @Autowired
    public JavaMailSender emailSender;
    
    /**Output a list of reader's books where status are in the set order by status, date of begin and date of end*/
    public Page<ReaderBook> listAll(Long id, Set<Long> idStatus, Pageable pageable) {
       	return readerBookRepo.findAllByIdUserAndIdStatusInOrderByIdStatusAscDateBeginAscDateEndAsc(id, idStatus, pageable);
    }
    
    /**Output a list of reader's books in Order*/
    public Page<ReaderBook> listDetails(Long id, Pageable pageable) {
       	return readerBookRepo.findReaderBookByOrders_Id(id, pageable);
    }
    
    public List<ReaderBook> listAll(Long id, Set<Long> idStatus) {
    	return readerBookRepo.findAllByIdUserAndIdStatusInOrderByIdStatusAscDateBeginAscDateEndAsc(id, idStatus);
    }
    
    public void save(ReaderBook readerBook) {
    	
    	readerBookRepo.save(readerBook);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d сохранил книгу id= %d в книгах читателя в базе", Application.id_user, readerBook.getId()));
    }
    
    /**Changing the status of a book*/
    public void save(Long id) {
    	
    	ReaderBook readerBook = get(id);
    	
		if (readerBook.getIdStatus() == 1) {
			readerBook.setIdStatus(2L);
			readerBook.setDateEnd(new Date());
		} else {
			readerBook.setIdStatus(1L);
			readerBook.setDateBegin(new Date());
		}
				
    	readerBookRepo.save(readerBook);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d изменил статус книги id= %d в книгах читателя в базе", Application.id_user, id));
    }
   
    /**Saving the reader's book in the database*/
    public void save(Long id, Long idBook, Long idPlace) {
    	
    	 ReaderBook readerBook = new ReaderBook();
			
		 readerBook.setDateBegin(new Date());
		 readerBook.setIdBook(idBook);
		 readerBook.setIdUser(id);
		 readerBook.setIdStatus(1L);
		 readerBook.setIdPlace(idPlace);
		 
		readerBookRepo.save(readerBook);
		
		LOGGER.info(String.format("Пользователь idUser= %d сохранил книгу id= %d в книгах читателя в базе", Application.id_user, idBook));
   }
    
    /**Issuing an order*/
    public void saveOut(Long id) {
    	
    	Orders orders = ordersService.get(id);
    	
    	orders.setIdStatus(2L);
    	for (ReaderBook readerBook : orders.getReaderBooks()) {
    		if (readerBook.getIdStatus() == 4) {
    			readerBook.setIdStatus(1L);
    			readerBook.setDateBegin(new Date());
    		} 
    	}
 		ordersService.save(id);
 		
 		LOGGER.info(String.format("Пользователь idUser= %d установил статус Заказана книги id= %d в книгах читателя в базе", Application.id_user, id));
    }

    public ReaderBook get(Long id) {
       return readerBookRepo.findById(id).get();
    }
    
    public void delete (ReaderBook readerBook) {
    	
    	readerBookRepo.delete(readerBook);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d удалил книгу id= %d из базы", Application.id_user, readerBook.getIdBook()));
    }
    
    /**Deleting the reader's book from the database*/
    public void delete (Long id) {
    	for (ReaderBook readerBook : ordersService.get(id).getReaderBooks()) {
    		
    		readerBookRepo.delete(get(readerBook.getId()));
    		
    		LOGGER.info(String.format("Пользователь idUser= %d удалил книгу id= %d из базы", Application.id_user, readerBook.getId()));
		}
    }
 
    /**Adding a book to the basket*/
    public void saveInBasket(Long idUser, Long idBook, Long idStatus, Long idPlace) {
    	
    	ReaderBook readerBook = new ReaderBook();
    	
    	readerBook.setIdBook(idBook);
    	readerBook.setIdPlace(idPlace);
    	readerBook.setIdStatus(idStatus);
    	readerBook.setIdUser(idUser);
    	
    	readerBookRepo.save(readerBook);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d добавил книги id= %d в корзину", Application.id_user, idBook));
     }
   
    /**Making an order*/
    public Integer createOrder(Long idUser) {
    	
    	Orders order = new Orders();
		order.setDateOrd(new Date());
		order.setIdStatus(1L);
		order.setIdUser(idUser);
	
		Long idOrder = ordersService.saveOrder(order).getId();
		List <String> listBookInOrder = new ArrayList<String>();	
		for (ReaderBook readerBook: listAll(idUser, Set.of(3L))) {
    		
    		readerBook.setIdStatus(4L);
    		save(readerBook);
    		
    		listBookInOrder.add(readerBook.getNameBook()); 
    		
    		ordersService.saveNewOrder(idOrder, readerBook.getId());
      	}
		
		if (!listBookInOrder.isEmpty()) {
			return  sendEmail(listBookInOrder, idOrder) ? 2 : 1;
		}
		
		LOGGER.info(String.format("Пользователь idUser= %d оформил заказ id= %d", Application.id_user, idOrder));
		
		return 1;
	}
    
    /**Sending email*/
    private Boolean sendEmail(List<String> listBookInOrder, Long idOrder) {
  
    	String email =  personRepo.findByIdIs(Application.id_user).getEmail();
    	
    	if (!email.equals("")) {
    	
    		SimpleMailMessage message = new SimpleMailMessage();
    		
	    	message.setTo(email);
	    	message.setSubject(String.format("Ваш заказ №%s оформлен", new SimpleDateFormat("ddMMyy").format(ordersService.get(idOrder).getDateOrd()) + idOrder));
	  
	    	String messageString = "Вы заказали: \n";
	    	int i = 0;
	    	for (String strBook: listBookInOrder) {
	    		i++;
	    		messageString = messageString + String.format("%d. %s \n", i, strBook);
	    	}
	    	message.setText(messageString);
	      	emailSender.send(message);
	      	return true;
    	}
    	return false;
     }
}