package by.pokumeiko.project.controller;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import by.pokumeiko.project.service.BookService;
import by.pokumeiko.project.service.KindOfBookService;
import by.pokumeiko.project.service.LanguageService;
import by.pokumeiko.project.service.PlaceService;
import by.pokumeiko.project.service.PublishingService;
import by.pokumeiko.project.service.ReaderBookService;
import by.pokumeiko.project.service.SubjectService;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

	@Autowired
	private BookService bookService;
	@Autowired
	private ReaderBookService readerBookService;
	@Autowired
	private LanguageService languageService;
	@Autowired
	private KindOfBookService kindOfBookService;
	@Autowired
	private PublishingService publishingService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private PlaceService placeService;
	
	private String authorName = "";
	private String bookName = "";
	private Long idpublishing = 1L;
	private Long idkindOfBook =  1L;
	private Long idsubject =  1L;
	private Long idlanguage =  1L;
	private Integer year = -1;
	
    @GetMapping(value = {"/", ""})
    public ModelAndView book(@RequestParam("pageSize") Optional<Integer> pageSize,
    						 @RequestParam("page") Optional<Integer> page, Model model){
    	model.addAttribute("book", new Book());
    	createModel(model);
      
        return pagination(pageSize, page);
    }
	
    private ModelAndView pagination(Optional<Integer> pageSize, Optional<Integer> page) {
    	
    	ModelAndView modelAndView = new ModelAndView("catalog");
        
        int evalPageSize = pageSize.orElse(Application.INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? Application.INITIAL_PAGE : page.get() - 1;
       
     
        Page<Book> listbooks = toPage(bookService.getSearch(authorName, bookName, idpublishing, idkindOfBook, idsubject, idlanguage, year), PageRequest.of(evalPage, evalPageSize, Sort.by("id")));
       
      	Pager pager = new Pager(listbooks.getTotalPages(),listbooks.getNumber(), Application.BUTTONS_TO_SHOW);
    
      	if ((listbooks.isEmpty()) & (year != -1) ) {
      	  	modelAndView.addObject("error", "Поиск не дал результатов");
      	}
      	if (year == -1) {
      	  	modelAndView.addObject("error", "Задайте критерии поиска");
      	}
	
      	modelAndView.addObject("listbooks", listbooks);
      	modelAndView.addObject("pager", pager);
      
      	modelAndView.addObject("pageSizes", Application.PAGE_SIZES);
      	modelAndView.addObject("selectedPageSize", evalPageSize);
      	
     
        return modelAndView;
    }
    
	/**Search in the catalog*/
	@PostMapping("/find")
	public String findBook(Book book, @RequestParam(required = false, name = "author") String author,
			Model model) throws SQLException  {
		
		year = book.getYear();
		authorName = author.trim().toUpperCase();
		bookName = book.getName().trim().toUpperCase();
		idpublishing = book.getIdpublishing();
		idkindOfBook = book.getIdkindOfBook();
		idsubject = book.getIdsubject();
		idlanguage = book.getIdlanguage();
		
		if ((year == null) & (authorName == "") & (bookName == "") & (idpublishing == 1) & (idkindOfBook == 1) & (idsubject == 1) & (idlanguage == 1)) {
			year = -1;
		} else {
			year = book.getYear() == null ? 0 : book.getYear();
		}
		
		return "redirect:/catalog";
	}
	
	/**Adding to basket*/
	@PostMapping("/inBasket/{id}")
	public String saveInBasket(@PathVariable("id") Long id,
								Model model, @RequestParam(required = false, name = "idPlace") Long idPlace) {
		
		readerBookService.saveInBasket(Application.id_user, id, 3L, idPlace);
			
		createModel(model);
		model.addAttribute("book", new Book());
		
		return "redirect:/catalog";
	}
		
	private void createModel(Model model) {
		
		model.addAttribute("listlanguages", languageService.listAllForBook());
		model.addAttribute("listkindOfBooks", kindOfBookService.listAllForBook());
		model.addAttribute("listpublishings", publishingService.listAllForBook());
		model.addAttribute("listsubjects", subjectService.listAllForBook());
		model.addAttribute("listPlaces", placeService.listAll());
	}
	
	/**Transformation List to Page*/
	private Page<Book> toPage(List<Book> list, Pageable pageable) {
		list.sort(Comparator.comparing(Book::getStringBook));
		
    	if (pageable.getOffset() >= list.size()) {
    		return Page.empty();
    	}
    	int startIndex = (int) pageable.getOffset();
    	int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ?
						    	list.size() :
						    	pageable.getOffset() + pageable.getPageSize());
    	
    	List<Book> subList = list.subList(startIndex, endIndex);
    	    	
    	return new PageImpl<Book>(subList, pageable, list.size());
	}
}