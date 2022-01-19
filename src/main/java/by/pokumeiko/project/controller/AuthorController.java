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
import by.pokumeiko.project.entity.Author;
import by.pokumeiko.project.entity.Pager;
import by.pokumeiko.project.service.AuthorService;

@Controller
@RequestMapping("/author")
public class AuthorController {
	@Autowired
    private AuthorService authorService;
	
	private Integer idError = 0;
	
	@GetMapping(value = {"/", ""})
	public ModelAndView author(@RequestParam("pageSize") Optional<Integer> pageSize,
							   @RequestParam("page") Optional<Integer> page,Model model) {
		
		return pagination(pageSize, page);
	}
	
	/**Adding an author*/
	@PostMapping("/addauthor")
	public String addAuthor(Author author) {
		
		authorService.save(author);
		
		return "redirect:/author";
	}
	
	/**Editing the author*/
	@PostMapping("/edit/{id}")
	public String editAuthor (@PathVariable("id") Long id, Model model,
							  @RequestParam(required = false, name = "fname") String fname,
							  @RequestParam(required = false, name = "iname") String iname,
							  @RequestParam(required = false, name = "oname") String oname) {
	    
		authorService.save(id, fname, iname, oname);
	    
	    return "redirect:/author";
	}
	
	/**Deleting an author*/
	@GetMapping("/delete/{id}")
	public String deleteAuthor(@PathVariable("id") long id, Model model) {
		
		if (authorService.delete(id)) {
			return "redirect:/author/";   
		}
		idError = 1;
		return "redirect:/author/";   
	}
	
	private ModelAndView pagination(Optional<Integer> pageSize, Optional<Integer> page) {
    	
    	ModelAndView modelAndView = new ModelAndView("author");
        
        int evalPageSize = pageSize.orElse(Application.INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? Application.INITIAL_PAGE : page.get() - 1;
       
        Page<Author> listAuthors = authorService.listAll(PageRequest.of(evalPage, evalPageSize));
       
      	Pager pager = new Pager(listAuthors.getTotalPages(), listAuthors.getNumber(), Application.BUTTONS_TO_SHOW);
   
      	
      	modelAndView.addObject("pageSizes", Application.PAGE_SIZES);
      	modelAndView.addObject("selectedPageSize", evalPageSize);
      	modelAndView.addObject("pager", pager);
      	
      	modelAndView.addObject("listauthors", listAuthors);
      	modelAndView.addObject("author", new Author());
      	modelAndView.addObject("lastselected", 1);
     
      	modelAndView.addObject("error", idError == 1 ? "Удаление записи невозможно!" : ""); 
      	idError = 0;
      	
        return modelAndView;
    }
}
