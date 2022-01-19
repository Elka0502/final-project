package by.pokumeiko.project.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.pokumeiko.project.Application;
import by.pokumeiko.project.entity.Orders;
import by.pokumeiko.project.repo.OrdersRepository;

@Service
@Transactional
public class OrdersService {
 
	private static final Logger LOGGER = LoggerFactory.getLogger(OrdersService.class);
	
    @Autowired
    private OrdersRepository ordersRepo;
    
    /**Output a list of orders order by date of order*/
    public Page<Orders> listAll(Long id, Pageable pageable) {
     	return ordersRepo.findByIdUserOrderByIdStatusAscDateOrdDescNumOrderDesc(id, pageable);
    }
    
    /**Saving the order in the database by id*/
    public void save(Long id) {
    	
    	ordersRepo.save(get(id));
    	
    	LOGGER.info(String.format("Пользователь idUser= %d сохранил заказ id= %d в базе", Application.id_user, id));
    }
 	
    /**Saving the order in the database and returning the saved order*/
    public Orders saveOrder(Orders orderBook) {
    	return  ordersRepo.save(orderBook);
    }
    
    public Orders get(Long id) {
       return ordersRepo.findById(id).get();
    }
    
    /**Return order by number*/
    public Orders findByNumOrder(String numOrder) {
        return ordersRepo.findByNumOrder(numOrder);
    }
   
    /**Output a list of books from an order by order number*/
    public Orders getOrderByReaderBook(Long id) {
        return ordersRepo.findByReaderBooks_Id(id);
    }
     
    /**Deleting an order from the database */
    public void delete(Long id) {
    	
       	ordersRepo.deleteById(id);
       	
       	LOGGER.info(String.format("Пользователь idUser= %d удалил заказ id= %d из базы", Application.id_user, id));
    }
    
    /**Saving book in an order*/
    public void saveNewOrder(Long id_order, Long id_reader_book) {
    	
    	ordersRepo.saveNewOrder(id_order, id_reader_book);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d сохранил заказ idOrder= %s idBook= %s в базе", Application.id_user, id_order, id_reader_book));
    	
    }
}