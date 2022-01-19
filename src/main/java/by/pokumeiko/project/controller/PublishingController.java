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
import by.pokumeiko.project.entity.Pager;
import by.pokumeiko.project.entity.Publishing;
import by.pokumeiko.project.service.PublishingService;

@Controller
@RequestMapping("/publishing")
public class PublishingController {
	
	@Autowired
    private PublishingService publishingService;
	
	private Integer idError = 0;
	
	@GetMapping(value = {"/", ""})
	public ModelAndView publishing(@RequestParam("pageSize") Optional<Integer> pageSize,
								   @RequestParam("page") Optional<Integer> page, Model model) {
		
		return pagination(pageSize, page);
	}
	
	/**Adding a publishing*/
	@PostMapping("/addpublishing")
	public String addPublishing(Publishing publishing) {
		
		publishingService.save(publishing);
		
		return "redirect:/publishing";
	}
	
	/**Editing the publishing*/
	@PostMapping("/edit/{id}")
	public String editPublishing (@PathVariable("id") Long id, @RequestParam(required = false, name = "name") String name,
										Model model) {
	    
		publishingService.save (id, name);
	    
	    return "redirect:/publishing";
	}
	
	/**Deleting a publishingr*/
	@GetMapping("/delete/{id}")
	public String deletePublishing(@PathVariable("id") long id, Model model) {
		
		if (publishingService.delete(id)) {
			 return "redirect:/publishing/";  
		}
		idError = 1;
		return "redirect:/publishing";  
	}
	
	private ModelAndView pagination(Optional<Integer> pageSize, Optional<Integer> page) {
    	
    	ModelAndView modelAndView = new ModelAndView("publishing");
        
        int evalPageSize = pageSize.orElse(Application.INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? Application.INITIAL_PAGE : page.get() - 1;
       
        Page<Publishing> listPublishings = publishingService.listAll(PageRequest.of(evalPage, evalPageSize));
       
      	Pager pager = new Pager(listPublishings.getTotalPages(), listPublishings.getNumber(), Application.BUTTONS_TO_SHOW);
   
      	modelAndView.addObject("pageSizes", Application.PAGE_SIZES);
      	modelAndView.addObject("selectedPageSize", evalPageSize);
      	modelAndView.addObject("pager", pager);
     
      	modelAndView.addObject("listPublishings", listPublishings);
      	modelAndView.addObject("publishing", new Publishing());
      	
      	modelAndView.addObject("error", idError == 1 ? "Удаление записи невозможно!" : ""); 
      	idError = 0;
      	
        return modelAndView;
    }
}
