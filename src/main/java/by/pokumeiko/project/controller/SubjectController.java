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
import by.pokumeiko.project.entity.Subject;
import by.pokumeiko.project.service.SubjectService;

@Controller
@RequestMapping("/subject")
public class SubjectController {
	@Autowired
    private SubjectService subjectService;
	
	private Integer idError = 0;
	
	@GetMapping(value = {"/", ""})
	public ModelAndView subject(@RequestParam("pageSize") Optional<Integer> pageSize,
			 					@RequestParam("page") Optional<Integer> page, Model model) {
		
		return pagination(pageSize, page);
	}
	
	/**Adding a subject*/
	@PostMapping("/addsubject")
	public String addSubject(Subject subject) {
			 
		subjectService.save(subject);
		
		return "redirect:/subject";
	}
	
	/**Editing the subject*/
	@PostMapping("/edit/{id}")
	public String editSubject (@PathVariable("id") Long id, @RequestParam(required = false, name = "name") String name,
										Model model) {
	 	subjectService.save(id, name);
	    
	    return "redirect:/subject";
	}
	
	/**Deleting a subject*/
	@GetMapping("/delete/{id}")
	public String deleteSubject(@PathVariable("id") long id, Model model) {
		    
	    if (subjectService.delete(id)) {
	    	return "redirect:/subject/";  
		}
	    idError = 1;
		return "redirect:/subject";  
	}
	
	private ModelAndView pagination(Optional<Integer> pageSize, Optional<Integer> page) {
    	
    	ModelAndView modelAndView = new ModelAndView("subject");
        
        int evalPageSize = pageSize.orElse(Application.INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? Application.INITIAL_PAGE : page.get() - 1;
       
        Page<Subject> listSubjects = subjectService.listAll(PageRequest.of(evalPage, evalPageSize));
       
      	Pager pager = new Pager(listSubjects.getTotalPages(), listSubjects.getNumber(), Application.BUTTONS_TO_SHOW);
   
      	modelAndView.addObject("pageSizes", Application.PAGE_SIZES);
      	modelAndView.addObject("selectedPageSize", evalPageSize);
      	modelAndView.addObject("pager", pager);
     
      	modelAndView.addObject("listSubjects", listSubjects);
      	modelAndView.addObject("subject", new Subject());
      	
      	modelAndView.addObject("error", idError == 1 ? "Удаление записи невозможно!" : ""); 
      	idError = 0;
      	
        return modelAndView;
    }
}