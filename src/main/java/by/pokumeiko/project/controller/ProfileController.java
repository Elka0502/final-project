package by.pokumeiko.project.controller;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import by.pokumeiko.project.Application;
import by.pokumeiko.project.entity.Orders;
import by.pokumeiko.project.entity.Pager;
import by.pokumeiko.project.entity.Person;
import by.pokumeiko.project.entity.ReaderBook;
import by.pokumeiko.project.service.BookService;
import by.pokumeiko.project.service.OrdersService;
import by.pokumeiko.project.service.PersonService;
import by.pokumeiko.project.service.PlaceService;
import by.pokumeiko.project.service.ReaderBookService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private PersonService personService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private ReaderBookService readerBookService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private PlaceService placeService;

	@GetMapping(value = {"/{id}"})
	public String profile (@PathVariable("id") Long id, Model model) {
	
		model.addAttribute("person", personService.get(id));
		model.addAttribute("profile", "profile");
		model.addAttribute("path", "profile/profileData");
	
		return "/profile";
	}
	
	/**Page of reader*/
	@GetMapping(value = {"/reader"})
	public ModelAndView profileReader (@RequestParam("pageSize") Optional<Integer> pageSize,
			 						   @RequestParam("page") Optional<Integer> page, Model model) {
	
		Page<ReaderBook> listBooks = readerBookService.listAll(Application.id_user, Set.of(3L), pageRequest(pageSize, page));
		
		return createModel(listBooks, Application.id_user, "/profile/profileBasket", pageSize.orElse(Application.INITIAL_PAGE_SIZE));
	}
	
	/**Page of reader's basket*/
	@GetMapping(value = {"/profileBasket"})
	public ModelAndView profileBasket (@RequestParam("pageSize") Optional<Integer> pageSize,
									   @RequestParam("page") Optional<Integer> page,
									   @RequestParam("id") Long id, 
									   @RequestParam(required = false, name = "saveOrder") String saveOrder,
									   @RequestParam(required = false, name = "saveEmail") String saveEmail,
									   Model model) {
		
		Page<ReaderBook> listBooks = readerBookService.listAll(id, Set.of(3L), pageRequest(pageSize, page));
		
		if (saveEmail != null) {
			model.addAttribute("error", String.format("Информация об оформленном заказе выслана на электронную почту %s", personService.get(Application.id_user).getEmail() ));
		}
		
		if (saveOrder != null) {
			model.addAttribute("error", "Заказ оформлен");
		}
		
		return createModel(listBooks, id, "/profile/profileBasket", pageSize.orElse(Application.INITIAL_PAGE_SIZE));
	}
	
	/**Page of reader's orders*/
	@GetMapping(value = {"/profileOrder"})
	public ModelAndView profileOrder (@RequestParam("pageSize") Optional<Integer> pageSize,
									  @RequestParam("page") Optional<Integer> page,
								      @RequestParam("id") Long id,
								      Model model) {
	
		Page<Orders> listBooks =  ordersService.listAll(id, pageRequest(pageSize, page));
     	
      	return createModel(listBooks, id, "/profile/profileOrder", pageSize.orElse(Application.INITIAL_PAGE_SIZE));
	}
	
	/**Page of reader's books*/
	@GetMapping(value = {"/profileBook"})
	public ModelAndView profileBook (@RequestParam("pageSize") Optional<Integer> pageSize,
									 @RequestParam("page") Optional<Integer> page,
									 @RequestParam("id") Long id,
									 Model model) {
	
		Page<ReaderBook> listBooks = readerBookService.listAll(id, Set.of(1L, 2L), pageRequest(pageSize, page));
	     
	    return createModel(listBooks, id, "/profile/profileBook",  pageSize.orElse(Application.INITIAL_PAGE_SIZE));
	}
	
	/**Page of book Issue*/
	@GetMapping(value = {"/profileGetBook/{id}"})
	public String profileGetBook (@PathVariable("id") Long id, Model model) {
		
		model.addAttribute("person", personService.get(id));
		model.addAttribute("path", "profile/profileGetBook");
		
		return "/profile";
	}
	
	/**Making an order*/
	@GetMapping(value = {"/createOrder/{id}"})
	public String createOrder (@PathVariable("id") Long idUser, Model model) {
		
		return readerBookService.createOrder(idUser) == 2 ? 
			   "redirect:/profile/profileBasket/?saveEmail=1&id=" + idUser :
			   "redirect:/profile/profileBasket/?saveOrder=1&id=" + idUser;
	}
	
	/**Page of reader's order details*/
	@GetMapping(value = {"/profileOrderDetails"})
	public ModelAndView profileOrderDetails (@RequestParam("pageSize") Optional<Integer> pageSize,
									   @RequestParam("page") Optional<Integer> page,
									   @RequestParam("id") Long id, 
									   Model model) {
	
		Page<ReaderBook> listBooks =  readerBookService.listDetails(id, pageRequest(pageSize, page));
    	
      	return createModel(listBooks, id, "/profile/profileOrderDetails", pageSize.orElse(Application.INITIAL_PAGE_SIZE));
    }
	
	/**Changing the reader's personal data*/
	@PostMapping(value = {"/editPerson"})
	public String editPerson (Person person, Model model) {
	
		personService.save(person);
		
		model.addAttribute("person", person);
		model.addAttribute("path", "profile/profileData");
		
		return "/profile";
	}
	
	/**Delivery and delivery of the book*/
	@GetMapping("/getInOutBook/{id}")
	public String getInOutBook (@PathVariable("id") Long id, Model model) {
	 	
		readerBookService.save(id);
		return "redirect:/profile/profileBook/?id=" +  readerBookService.get(id).getIdUser();
	}

	/**Deleting a book from an order*/
	@GetMapping("/deleteBookFromOrder/{id}")
	public String deleteBookFromOrder (@PathVariable("id") Long id, Model model) {
	    
		Long idOrder = ordersService.getOrderByReaderBook(id).getId();
		readerBookService.delete(readerBookService.get(id));
			
		return "redirect:/profile/profileOrderDetails/?id=" + idOrder;
	}
	
	/**Deleting a book from a basket*/
	@GetMapping("/deleteBookFromBasket/{id}")
	public String deleteBookFromBasket (@PathVariable("id") Long id, Model model) {
	    
		readerBookService.delete(readerBookService.get(id));
		
		return "redirect:/profile/profileBasket/?id=" + Application.id_user;
	}
	
	/**Deleting an order*/
	@GetMapping("/deleteOrder/{id}")
	public String deleteOrder (@PathVariable("id") Long id, Model model) {
	 	
		readerBookService.delete(id);
		
		Long idUser = ordersService.get(id).getIdUser();
		ordersService.delete(id);
			
		return "redirect:/profile/profileOrder/?id=" + idUser;
	}

	/**Delivery the order*/
	@GetMapping("/getOutOrder/{id}")
	public String getOutOrder (@PathVariable("id") Long id, Model model) {
	    
		readerBookService.saveOut(id);
		
		return "redirect:/profile/profileOrder/?id=" + ordersService.get(id).getIdUser();
	}
	
	/**Issuance of books by the librarian from the search*/
	@PostMapping("/getInFindingBook/{id}")
	public String findBook(Model model, @PathVariable("id") Long id,
						   @RequestParam("idBook") Long idBook,
						   @RequestParam("idPlace") Long idPlace) {
	
		readerBookService.save(id, idBook, idPlace);
	
		return "redirect:/profile/profileBook/?id=" + id;
	}
	
	/**Search for books by a librarian*/
	@PostMapping("/findBook/{id}")
	public String findBook(@PathVariable("id") Long id,
						   @RequestParam(required = false, name = "author") String author,
						   @RequestParam(required = false, name = "numBook") String numBook,	
						   @RequestParam(required = false, name = "bookName") String bookName,	Model model) throws SQLException  {
	
		 model.addAttribute("authorFind", author);
		 model.addAttribute("numBookFind", numBook);
		 model.addAttribute("bookNameFind", bookName);
		 
		 if (bookService.find(numBook, author, bookName).isEmpty()) {
			model.addAttribute("error", "Поиск не дал результатов");
		 } else {
			model.addAttribute("listbooks", bookService.find(numBook, author, bookName));
			model.addAttribute("listPlaces", placeService.listAll());
		 }
		
		 model.addAttribute("person", personService.get(id));
		 model.addAttribute("path", "profile/profileGetBook"); 
		
		 return "/profile";
	}
	
	/**Search order by number*/
	@PostMapping("/searchOrder")
	public String searchOrderByNumber (@RequestParam(required = false, name = "numOrder") String numOrder,
									   Model model) {
	    if (ordersService.findByNumOrder(numOrder) != null) {
	    	return "redirect:/profile/profileOrderDetails/?id=" + ordersService.findByNumOrder(numOrder).getId();
		}
	   
	    return "redirect:/person/readers/?notFoundOrder=" + "";
	}
	
	private ModelAndView createModel(Page<?> listPage, Long id, String path, int evalPageSize) {
		
		ModelAndView modelAndView = new ModelAndView("profile");
		
		Pager pager = new Pager(listPage.getTotalPages(), listPage.getNumber(), Application.BUTTONS_TO_SHOW); 
		
		modelAndView.addObject("listBooks", listPage);
		modelAndView.addObject("pageSizes", Application.PAGE_SIZES); 
		modelAndView.addObject("pager", pager); 
		modelAndView.addObject("path", path); 
	 	modelAndView.addObject("selectedPageSize", evalPageSize);
	 	
	 	if (path.equals("/profile/profileOrderDetails")) {
	 		Orders orders = ordersService.get(id);
	 		modelAndView.addObject("orderBook", orders);
	 		modelAndView.addObject("person", personService.get(orders.getIdUser())); 
	 		modelAndView.addObject("pagination_details", "pagination_details");
	 	} else {
	 		modelAndView.addObject("person", personService.get(id)); 
	 		modelAndView.addObject("pagination", "pagination");
	 	}
	 	return modelAndView;
	 }
	
	private PageRequest pageRequest(Optional<Integer> pageSize, Optional<Integer> page)  {
		
		int evalPageSize = pageSize.orElse(Application.INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? Application.INITIAL_PAGE : page.get() - 1;
        
        return PageRequest.of(evalPage, evalPageSize);
	}
}