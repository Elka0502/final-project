package by.pokumeiko.project.controller;

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
import by.pokumeiko.project.entity.KindOfBook;
import by.pokumeiko.project.entity.Pager;
import by.pokumeiko.project.service.KindOfBookService;

@Controller
@RequestMapping("/kindOfBook")
public class KindOfBookController {
	@Autowired
    private KindOfBookService kindOfBookService;
	
	private Integer idError = 0;
	
	@GetMapping(value = {"/", ""})
	public ModelAndView kindOfBook(@RequestParam("pageSize") Optional<Integer> pageSize,
								   @RequestParam("page") Optional<Integer> page, Model model) {
		
		return pagination(pageSize, page);
	}
	
	/**Adding a kind of book*/
	@PostMapping("/addkindOfBook")
	public String addKindOfBook(KindOfBook kindOfBook) {
		
		kindOfBookService.save(kindOfBook);
		
		return "redirect:/kindOfBook";
	}
	
	/**Editing the kind of book*/
	@PostMapping("/edit/{id}")
	public String editKindOfBook (@PathVariable("id") Long id, @RequestParam(required = false, name = "name") String name,
										Model model) {
	    
		kindOfBookService.save(id, name);
	    
	    return "redirect:/kindOfBook";
	}
	
	/**Deleting a kind of book*/
	@GetMapping("/delete/{id}")
	public String deleteKindOfBook(@PathVariable("id") long id, Model model) {
		
		if (kindOfBookService.delete(id)) {
			
		   return "redirect:/kindOfBook/";
		}
	
		idError = 1;
		return "redirect:/kindOfBook";  
	          
	}
	
	private ModelAndView pagination(Optional<Integer> pageSize, Optional<Integer> page) {
    	
    	ModelAndView modelAndView = new ModelAndView("kindOfBook");
        
        int evalPageSize = pageSize.orElse(Application.INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? Application.INITIAL_PAGE : page.get() - 1;
       
        Page<KindOfBook> listKindOfBooks = kindOfBookService.listAll(PageRequest.of(evalPage, evalPageSize));
       
      	Pager pager = new Pager(listKindOfBooks.getTotalPages(), listKindOfBooks.getNumber(), Application.BUTTONS_TO_SHOW);
    
       	modelAndView.addObject("pageSizes", Application.PAGE_SIZES);
      	modelAndView.addObject("selectedPageSize", evalPageSize);
      	modelAndView.addObject("pager", pager);
     
      	modelAndView.addObject("listKindOfBooks", listKindOfBooks);
      	modelAndView.addObject("kindOfBook", new KindOfBook());
      	
      	modelAndView.addObject("error", idError == 1 ? "Удаление записи невозможно!" : ""); 
      	idError = 0;
      	
        return modelAndView;
    }
}