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
import by.pokumeiko.project.entity.Book;
import by.pokumeiko.project.entity.Pager;
import by.pokumeiko.project.service.AuthorService;
import by.pokumeiko.project.service.BookService;
import by.pokumeiko.project.service.KindOfBookService;
import by.pokumeiko.project.service.LanguageService;
import by.pokumeiko.project.service.PublishingService;
import by.pokumeiko.project.service.SubjectService;

@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private LanguageService languageService;
	@Autowired
	private KindOfBookService kindOfBookService;
	@Autowired
	private PublishingService publishingService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private SubjectService subjectService;
	
	private Integer idError = 0;
	
	@GetMapping(value = {"/", ""})
	public ModelAndView book (@RequestParam("pageSize") Optional<Integer> pageSize,
							  @RequestParam("page") Optional<Integer> page, Model model) {
		return pagination(pageSize, page);
	}
	
	/**Adding a book*/
	@PostMapping("/addbook")
	public String addBook(Book book, @RequestParam(required = false, name = "authorid") List <Long> authorId, Model model) {
		
		bookService.save(book, authorId);
		
		return "redirect:/book";
	}
	
	/**Editing the book*/
	@PostMapping("/edit/{id}")
	public String editBook (@PathVariable("id") Long id, 
							@RequestParam(required = false, name = "authorid") List<Long> authorId,
							@RequestParam(required = false, name = "name") String name,
							@RequestParam(required = false, name = "idpublishing") Long idpublishing,
							@RequestParam(required = false, name = "idkindOfBook") Long idkindOfBook,
							@RequestParam(required = false, name = "idsubject") Long idsubject,
							@RequestParam(required = false, name = "idlanguage") Long idlanguage,
							@RequestParam(required = false, name = "year") Integer year,
							@RequestParam(required = false, name = "count") Integer count) {
		
		bookService.save(id, authorId, name, idpublishing, idkindOfBook, idsubject, idlanguage, year, count);
		
		return "redirect:/book";
	}
	
	/**Deleting a book*/
	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable("id") long id, Model model) {
		
		if (!bookService.delete(id)) {
			
			idError = 1;
			return "redirect:/book/";   
		}
	    return "redirect:/book/";       
	}
	
	private ModelAndView pagination(Optional<Integer> pageSize, Optional<Integer> page) {
    	
    	ModelAndView modelAndView = new ModelAndView("book");
        
        int evalPageSize = pageSize.orElse(Application.INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? Application.INITIAL_PAGE : page.get() - 1;
       
        Page<Book> listbooks = bookService.listAll(PageRequest.of(evalPage, evalPageSize));
       
      	Pager pager = new Pager(listbooks.getTotalPages(), listbooks.getNumber(), Application.BUTTONS_TO_SHOW);
   
      	modelAndView.addObject("pageSizes", Application.PAGE_SIZES);
      	modelAndView.addObject("selectedPageSize", evalPageSize);
      	modelAndView.addObject("pager", pager);
     
      	modelAndView.addObject("listbooks", listbooks);
      	modelAndView.addObject("listlanguages", languageService.listAllForBook());
      	modelAndView.addObject("listkindOfBooks", kindOfBookService.listAllForBook());
      	modelAndView.addObject("listpublishings", publishingService.listAllForBook());
      	modelAndView.addObject("listsubjects", subjectService.listAllForBook());
      	modelAndView.addObject("listauthors", authorService.listAll());
      	modelAndView.addObject("book", new Book());
      	
      	modelAndView.addObject("error", idError == 1 ? "Удаление записи невозможно!" : ""); 
      	idError = 0;
      	
        return modelAndView;
    }
}