package by.pokumeiko.project.controller;

import java.util.List;
import java.util.Optional;

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
import by.pokumeiko.project.entity.Pager;
import by.pokumeiko.project.entity.User;
import by.pokumeiko.project.service.AccessService;

@Controller
@RequestMapping("/access")
public class AccessController {
		
	@Autowired
    private AccessService accessService;
	
	private Integer idError = 0;
	
	@GetMapping(value = {"/", ""})
	public ModelAndView access(@RequestParam("pageSize") Optional<Integer> pageSize,
			 				   @RequestParam("page") Optional<Integer> page, Model model) {
		return pagination(pageSize, page);
	}
	
	/**Adding a user by an administrator*/
	@PostMapping("/addaccess")
	public String insertAccess(User user, @RequestParam(required = false, name = "roleid") List<Long> roleId, Model model) {
			
		if (!accessService.save(user, roleId))   {
		
			idError = 2;
			return "redirect:/access";
		}
		return "redirect:/access";
	}
	
	/**Deleting a user by an administrator*/
	@GetMapping("/delete/{id}")
	public String deleteAccess(@PathVariable("id") Long id, Model model) {
		
		if (!accessService.delete(id)) {
			idError = 1;
			return "redirect:/access";
		} 
		return "redirect:/access";
	}
	
	private ModelAndView pagination(Optional<Integer> pageSize, Optional<Integer> page) {
    	
    	ModelAndView modelAndView = new ModelAndView("access");
        
        int evalPageSize = pageSize.orElse(Application.INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? Application.INITIAL_PAGE : page.get() - 1;
       
        Page<User> listAccess = accessService.loadUserList(PageRequest.of(evalPage, evalPageSize));
       
      	Pager pager = new Pager(listAccess.getTotalPages(), listAccess.getNumber(), Application.BUTTONS_TO_SHOW);
   
      	modelAndView.addObject("pageSizes", Application.PAGE_SIZES);
      	modelAndView.addObject("selectedPageSize", evalPageSize);
      	modelAndView.addObject("pager", pager);
     
      	modelAndView.addObject("listAccess", listAccess);
      	modelAndView.addObject("listRole", accessService.loadRoleList());
      	modelAndView.addObject("access", new User());
      	
        modelAndView.addObject("error", idError == 1 ? "Невозможно удалить пользователя" : idError == 2 ?  "Пользователь с таким именем уже существует" : ""); 
     	idError = 0;
      	
        return modelAndView;
    }
}
