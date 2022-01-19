package by.pokumeiko.project.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import by.pokumeiko.project.Application;
import by.pokumeiko.project.entity.Pager;
import by.pokumeiko.project.entity.Person;
import by.pokumeiko.project.service.AccessService;
import by.pokumeiko.project.service.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@Autowired
	private AccessService accessService;
	
	@GetMapping(value = {"/", ""})
	public String person (Model model) {
		
		model.addAttribute("person", personService.get(Application.id_user));
		return "/person";
	}
	
	/**Changing personal data*/
	@PostMapping("/updatePerson")
	public String updatePerson(Person person, Model model) {
	
		person.setId(Application.id_user);
		personService.save(person);
		model.addAttribute("passwordMessage", "Данные сохранены"); 
		
		return "/person";   
	}
	
	/**Changing personal data by reader*/
	@PostMapping("/updatePersonReader")
	public String updatePersonReader(Person person, @RequestParam(required = false, name = "phone") String phone,
									 @RequestParam(required = false, name = "email") String email,
									 Model model) {
		
		personService.saveAccount(email, phone, Application.id_user);
		model.addAttribute("passwordMessage", "Данные сохранены"); 
		
		return "/person";   
	}
	
	/**Page of list of readers*/
	@GetMapping(value = {"/readers"})
	public ModelAndView readers (@RequestParam("pageSize") Optional<Integer> pageSize,
								 @RequestParam("page") Optional<Integer> page, 
								 @RequestParam(required = false, name = "notFoundOrder") String notFoundOrder,
								 Model model) {
	
		if (notFoundOrder != null) {
			model.addAttribute("saveError", "Заказ с заданным номером не найден");
		}
		return pagination(pageSize, page);
	}
		
	/**Page for saving a new person*/
	@GetMapping(value = {"/newPerson"})
	public String newPerson (Model model) {
        
		model.addAttribute("newPerson", "newPerson");
		model.addAttribute("person", new Person());
		
		return "/person";
	}
	
	/**Saving a new reader*/
	@PostMapping(value = {"/saveNewPerson"})
	public ModelAndView saveNewPerson (@RequestParam("pageSize") Optional<Integer> pageSize,
									   @RequestParam("page") Optional<Integer> page,
			 						   Person person, Model model) {
	
		personService.save(person);
		model.addAttribute("saveError", "Новый читатель сохранён"); 
	
		return pagination(pageSize, page);
	}
	
	/**Page for saving a new password or user*/
	@GetMapping(value = {"/newPasswordLogin/"})
	public String changePassword (Model model) {
		
		model.addAttribute("passwordFirst", "");
		model.addAttribute("passwordSecond", "");
		
		return "/newPasswordLogin";
	}
	
	/**Changing the password*/
	@PostMapping("/newPassword")
	public String newPassword (@RequestParam(required = false, name = "passwordFirst") String passwordFirst,
			 				   @RequestParam(required = false, name = "passwordSecond") String passwordSecond, Model model) {
			
		if (personService.saveNewPass(passwordFirst, passwordSecond)) {
			
			model.addAttribute("passwordMessage", "Пароль сохранен"); 
			return "/newPasswordLogin";    
		} 
		
		model.addAttribute("passwordMessage", "Пароли не совпадают"); 	
		return "/newPasswordLogin";   
	}
	
	/**Registering a new reader*/
	@PostMapping("/newLogin")
	public String newUser (@RequestParam(required = false, name = "userName") String userName,
						   @RequestParam(required = false, name = "passwordFirst") String passwordFirst,
			 			   @RequestParam(required = false, name = "passwordSecond") String passwordSecond, Model model) {
		
		if (personService.getUserByUserName(userName) != null) {
			
			model.addAttribute("passwordMessage", "Пользователь с таким именем уже существует"); 
			
			return "/newPasswordLogin";
		}
		
		if (accessService.save(userName, passwordFirst, passwordSecond)) {
			
			model.addAttribute("saveUserMessage", "Пользователь сохранен"); 
			return "/login";       
		}
		
		model.addAttribute("passwordMessage", "Пароли не совпадают"); 	
		return "/newPasswordLogin";
	}
	
	private ModelAndView pagination(Optional<Integer> pageSize, Optional<Integer> page) {
    	
    	ModelAndView modelAndView = new ModelAndView("readers");
        
        int evalPageSize = pageSize.orElse(Application.INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? Application.INITIAL_PAGE : page.get() - 1;
       
        Page<Person> listReaders = personService.getReaders(PageRequest.of(evalPage, evalPageSize));
        
      	Pager pager = new Pager(listReaders.getTotalPages(), listReaders.getNumber(), Application.BUTTONS_TO_SHOW);
   
      	modelAndView.addObject("pageSizes", Application.PAGE_SIZES);
      	modelAndView.addObject("selectedPageSize", evalPageSize);
      	modelAndView.addObject("pager", pager);
     	modelAndView.addObject("listReaders", listReaders);
      	
        return modelAndView;
    }
}