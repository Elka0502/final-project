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
import by.pokumeiko.project.entity.Language;
import by.pokumeiko.project.entity.Pager;
import by.pokumeiko.project.service.LanguageService;

@Controller
@RequestMapping("/language")
public class LanguageController {

	@Autowired
    private LanguageService languageService;
	
	private Integer idError = 0;
		  
	@GetMapping(value = {"/", ""})
	public ModelAndView language(@RequestParam("pageSize") Optional<Integer> pageSize,
			 					 @RequestParam("page") Optional<Integer> page, Model model) {
		return pagination(pageSize, page);
	}
	
	/**Adding a language*/
	@PostMapping("/addlanguage")
	public String addLanguage(Language language) {
		
		languageService.save(language);
		return "redirect:/language";
	}
	
	/**Editing the language*/
	@PostMapping("/edit/{id}")
	public String editLanguage (@PathVariable("id") Long id, @RequestParam(required = false, name = "name") String name,
										Model model) {
			
		languageService.save(id, name);
	    return "redirect:/language";
	}
	
	/**Deleting a language*/
	@GetMapping("delete/{id}")
	public String deleteLanguage(@PathVariable("id") Long id, Model model) {
		
		if (languageService.delete(id)) {
			return "redirect:/language";  
		}
	
		idError = 1;
		return "redirect:/language";   
	}
	
	private ModelAndView pagination(Optional<Integer> pageSize, Optional<Integer> page) {
    	
    	ModelAndView modelAndView = new ModelAndView("language");
        
        int evalPageSize = pageSize.orElse(Application.INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? Application.INITIAL_PAGE : page.get() - 1;
       
        Page<Language> listLanguages = languageService.listAll(PageRequest.of(evalPage, evalPageSize));
       
      	Pager pager = new Pager(listLanguages.getTotalPages(), listLanguages.getNumber(), Application.BUTTONS_TO_SHOW);
   
      	modelAndView.addObject("listLanguages", listLanguages);
      	modelAndView.addObject("pageSizes", Application.PAGE_SIZES);
      	modelAndView.addObject("selectedPageSize", evalPageSize);
      	modelAndView.addObject("pager", pager);
     
      	modelAndView.addObject("language", new Language());
      
      	modelAndView.addObject("error", idError == 1 ? "Удаление записи невозможно!" : ""); 
      	idError = 0;
      	
        return modelAndView;
    }
}
